package com.driver;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class WhatsappService {
    WhatsappRepository whatsappRepository = new WhatsappRepository();
    public String createUser(String name, String mobile) throws Exception {
        if(whatsappRepository.checkUser(mobile)) throw new Exception("User already exists");
        User user = new User(name,mobile);
        return whatsappRepository.addUser(mobile,user);
    }


    public Group createGroup(List<User> users){
        int count = users.size();
        Group group;
        if(count==2){
            group = new Group(users.get(1).getName(),2);
        }
        else{
            int groupCount = whatsappRepository.getCustomGroupCount();
            group = new Group("Group"+(groupCount+1),count);
            whatsappRepository.setCustomGroupCount(groupCount+1);
        }
        whatsappRepository.createGroup(group,users);
        return group;
    }


    public int createMessage(String content){
        int count = whatsappRepository.getMessageId()+1;
        Message message = new Message(count,content);
        whatsappRepository.setMessageId(count);
        return count;
    }


    public int sendMessage(Message message, User sender, Group group) throws Exception{
        if(whatsappRepository.checkGroup(group)) throw new Exception("Group does not exist");
        if(!findUser(group,sender)) throw new Exception("You are not allowed to send message");
        return whatsappRepository.addMessage(message,sender,group);
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception{
        if(whatsappRepository.checkGroup(group)) throw new Exception("Group does not exist");
        if(whatsappRepository.checkAdmin(approver,group)) throw new Exception("Approver does not have rights");
        if(!findUser(group,user)) throw new Exception("User is not a participant");
        return whatsappRepository.changeAdmin(group,user);
    }


    public int removeUser(User user) throws Exception{
        HashMap<Group, List<User>> map = whatsappRepository.getGroups();
        boolean isfind = false;
        Group isGroup = null;
        for(Group group : map.keySet()){
            if(findUser(group,user)){
                isfind = true;
                isGroup = group;
                break;
            }
        }
        if(!isfind) throw new Exception("User not found");
        if(whatsappRepository.isAdmin(isGroup,user)) throw new Exception("Cannot remove admin");
        return whatsappRepository.removeUser(user);
    }


    public String findMessage(Date start, Date end, int K) throws Exception{
        return  null;
    }
    public boolean findUser(Group group , User sender){
        List<User> users = whatsappRepository.getUsers(group);
        boolean isuser = false;
        for(User user : users){
            if(user==sender){
                isuser=true;
                break;
            }
        }
        return isuser;
    }
}
