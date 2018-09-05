package org.romanchi.commands;

import org.romanchi.database.dao.HintDao;
import org.romanchi.database.dao.HintDaoFactory;
import org.romanchi.database.entities.Hint;
import org.romanchi.database.entities.Question;
import org.romanchi.database.DatabaseUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 05.09.18
 * Time: 1:09
 * To change this template use File | Settings | File Templates.
 */
public class HintCommand implements Command {
    private final static Logger logger = Logger.getLogger(HintCommand.class.getName());
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long hintTypeId = Long.valueOf(request.getParameter("hintTypeId"));
        long questionId = ((Question) request.getSession().getAttribute("question")).getQuestionId();
        //HintTypeDao hintTypeDao = HintTypeDaoFactory.create();
        HintDao hintDao = HintDaoFactory.create();
        try {
            Hint[] hintsQuestionId = hintDao.findWhereQuestionIdEquals(questionId);
            List<Hint> hintList = Stream.of(hintsQuestionId).filter((hint)->hint.getHintType().getHintTypeId().equals(hintTypeId)).collect(Collectors.toList());
            if(hintList.size() != 0){
                Hint hintToReturn = (hintList.size() != 0) ? hintList.get(0) : null;
                request.setAttribute("hint", hintToReturn.getHintDescription());
                return "/question.jsp";
            }else{
                return "/question.jsp";
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
            request.setAttribute("messageError", "Database exception");
            return "/error.jsp";
        }
    }
}
