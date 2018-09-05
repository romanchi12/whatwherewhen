import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.romanchi.commands.LoginCommand;
import org.romanchi.database.dao.UserDao;
import org.romanchi.database.dao.UserDaoFactory;
import org.romanchi.database.entities.User;
import org.romanchi.database.DatabaseUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 04.09.18
 * Time: 22:13
 * To change this template use File | Settings | File Templates.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({UserDaoFactory.class,DatabaseUtils.class})
public class TestLoginCommand {
    HttpServletRequest request;
    HttpServletResponse response;
    DataSource dataSource;
    UserDao userDao;
    User user;
    HttpSession session;
    @Before
    public void beforeTest(){
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dataSource = mock(DataSource.class);
        userDao = mock(UserDao.class);
        user = mock(User.class);
        session = mock(HttpSession.class);
        PowerMockito.mockStatic(UserDaoFactory.class);
        PowerMockito.mockStatic(DatabaseUtils.class);

    }
    @Test()
    public void testLoginCommandWithGoodParameters() throws Exception {
        when(request.getParameter("userlogin")).thenReturn("user4");
        when(request.getParameter("userpassword")).thenReturn("pass");
        when(DatabaseUtils.getDataSource()).thenReturn(dataSource);
        when(UserDaoFactory.create(DatabaseUtils.getDataSource())).thenReturn(userDao);
        when(userDao.findWhereUserLoginAndPasswordEquals("user4", "pass")).thenReturn(user);
        when(request.getSession()).thenReturn(session);
        doNothing().when(session).setAttribute("user", user);

        LoginCommand loginCommand = new LoginCommand();
        String resultPath = loginCommand.execute(request, response);

        verify(request, never()).setAttribute("messageError", "Bad login or password");
        verify(request, never()).setAttribute("messageError", "Database error");

        verify(request, times(1)).setAttribute("user", user);
        verify(session,times(1)).setAttribute("user", user);

        assertEquals("/profile.jsp", resultPath);

    }
    @Test
    public void testLoginCommandWithWrongParameters() throws Exception {
        when(request.getParameter("userlogin")).thenReturn(null);
        when(request.getParameter("userpassword")).thenReturn(null);
        when(DatabaseUtils.getDataSource()).thenReturn(dataSource);
        when(UserDaoFactory.create(DatabaseUtils.getDataSource())).thenReturn(userDao);
        when(userDao.findWhereUserLoginAndPasswordEquals(null, null)).thenReturn(null);
        when(request.getSession()).thenReturn(session);

        LoginCommand loginCommand = new LoginCommand();
        String resultPath = loginCommand.execute(request, response);

        verify(request, never()).setAttribute("messageError", "Database error");

        verify(request, times(1)).setAttribute("messageError", "Bad login or password");

        assertEquals("/login.jsp", resultPath);
    }
    @Test
    public void testLoginCommandWithDaoException() throws Exception{
        when(request.getParameter("userlogin")).thenReturn("user4");
        when(request.getParameter("userpassword")).thenReturn("pass");
        when(DatabaseUtils.getDataSource()).thenReturn(dataSource);
        when(UserDaoFactory.create(DatabaseUtils.getDataSource())).thenReturn(userDao);
        doThrow(new SQLException()).when(userDao).findWhereUserLoginAndPasswordEquals("user4", "pass");

        LoginCommand loginCommand = new LoginCommand();
        String resultPath = loginCommand.execute(request, response);

        verify(request, never()).setAttribute("messageError", "Bad login or password");
        verify(request, never()).setAttribute("user", user);
        verify(request).setAttribute("messageError", "Database error");

        assertEquals("/error.jsp", resultPath);

    }
}
