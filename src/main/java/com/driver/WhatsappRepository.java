package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<User,Group> usergroupMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<User,List<Message>> userMessageHashMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public int getCustomGroupCount() {
        return customGroupCount;
    }

    public void setCustomGroupCount(int customGroupCount) {
        this.customGroupCount = customGroupCount;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMessageHashMap = new HashMap<>();
        this.usergroupMap = new HashMap<>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String addUser(String mobile,User user){
        userMobile.add(mobile);
        //userMessageHashMap.put(user,new ArrayList<>());
        return "SUCCESS";
    }
    public  int addMessage(Message message, User sender, Group group){
        groupMessageMap.get(group).add(message);
        senderMap.put(message,sender);
        //userMessageHashMap.get(sender).add(message);
        return groupMessageMap.get(group).size();
    }
    public void createGroup(Group group,List<User> users){
        groupUserMap.put(group,users);
        groupMessageMap.put(group,new ArrayList<>());
        adminMap.put(group,users.get(0));
    }
    public boolean checkUser(String mobile){
        if(userMobile.contains(mobile)) return true;
        return false;
    }
    public boolean checkGroup(Group group){
        if(!groupUserMap.containsKey(group)) return true;
        return false;
    }
    public boolean checkAdmin(User approver, Group group){
        if(adminMap.get(group)!=approver) return true;
        return false;
    }
    public List<User> getUsers(Group group){
        return groupUserMap.get(group);
    }
    public HashMap<Group,List<User>> getGroups(){
        return groupUserMap;
    }

    public String changeAdmin(Group group,User newAdmin){
        adminMap.put(group,newAdmin);
        return "SUCCESS";
    }
    public boolean isAdmin(Group group,User user){
        if(adminMap.get(group)==user) return true;
        return false;
    }
    public int removeUser(User user){
        return 0;
    }
}
