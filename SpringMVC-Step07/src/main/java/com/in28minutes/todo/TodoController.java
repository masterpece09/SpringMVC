package com.in28minutes.todo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.in28minutes.model.Todo;
import com.in28minutes.todo.service.TodoService;

@Controller
@SessionAttributes("name")
public class TodoController {
 
	@Autowired
    private TodoService service; 
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping(value = "/list-todos", method = RequestMethod.GET)
    public String showTodoList(ModelMap model, String name) {
    	//throw new RuntimeException("Dummy Exception");
    	//model = null;
        String user = (String) model.get("name");
        model.addAttribute("todos", service.retrieveTodos("toto"));
        return "list-todos";
    } 

    @RequestMapping(value = "/add-todo", method = RequestMethod.GET)
    public String showTodoPage(ModelMap model) {
    	model.put("todo", new Todo(0, "toto", "Default", new Date(), false));
    	model.put("url","/add-todo");
        return "todo"; 
    } 

    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
    	
    	if(result.hasErrors()){
    		return "todo";
    	}
    	
        //service.addTodo((String) model.get("name"), todo.getDesc(), new Date(), false);
    	service.addTodo(getLoggedInUserName(model), todo.getDesc(), new Date(), false);
        model.clear();// to prevent request parameter "name" to be passed
        return "redirect:/list-todos";
    }  
    
    private String getLoggedInUserName(ModelMap model) {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (principal instanceof UserDetails)
            return ((UserDetails) principal).getUsername();

        return principal.toString();
    }
    
    @RequestMapping(value="/delete-todo", method = RequestMethod.GET)
    public String deleteTodo(@RequestParam int id){
    	
    	service.deleteTodo(id);
    	
    	return "redirect:/list-todos";
    }
    
    @RequestMapping(value = "/update-todo", method = RequestMethod.GET)
    public String showUpdateTodoPage(ModelMap model, @RequestParam int id) {
        model.addAttribute("todo", service.retrieveTodo(id));
        model.put("url","/update-todo");
        return "todo";
    }

    @RequestMapping(value = "/update-todo", method = RequestMethod.POST)
    public String updateTodo(ModelMap model, @Valid Todo todo,
            BindingResult result) {
        if (result.hasErrors())
            return "todo";

        //todo.setUser("in28Minutes"); //TODO:Remove Hardcoding Later
        todo.setUser(getLoggedInUserName(model));
        service.updateTodo(todo);

        model.clear();// to prevent request parameter "name" to be passed
        return "redirect:/list-todos";
    }
    
}