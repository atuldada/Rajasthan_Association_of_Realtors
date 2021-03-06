/**
 * Copyright (c) 2015 Cloudant, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package io.jaipurheights.rar;

import com.cloudant.sync.datastore.Attachment;

import com.cloudant.sync.datastore.DocumentRevision;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/*
 * Object representing a task.
 *
 * As well as acting as a value object, this class also has a reference to the original
 * DocumentRevision, which will be valid if the Task was fetched from the database, or else null
 * (eg for Tasks which have been created but not yet saved to the database).
 *
 * fromRevision() and asMap() act as helpers to map to and from JSON - in a real application
 * something more complex like an object mapper might be used.
 */

public class Task {

    private Task() {}

    public Task(String desc,String name,String phone ,String subdescription,String city,String location ,String price,String area,String imagename,String Formtype,String identity) {

        this.setDescription(desc);
        this.setName(name);
        this.setPhone(phone);
        this.setSubdescription(subdescription);
        this.setCity(city);
        this.setLocation(location);
        this.setPrice(price);
        this.setArea(area);
        this.setInfo(info);
        this.setCompleted(false);
        this.setType(DOC_TYPE);
        this.setImagename(imagename);
        this.setFormtype(Formtype);
        this.setIdentity(identity);

    }

    // this is the revision in the database representing this task
    private DocumentRevision rev;
    public DocumentRevision getDocumentRevision() {
        return rev;
    }

    static final String DOC_TYPE = "com.cloudant.sync.example.task";
    private String type = DOC_TYPE;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    private boolean completed;
    public boolean isCompleted() {
        return this.completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    private String description;
    private String subdescription =" ";
    private String location =" ";
    private String phone =" ";
    private String name=" ";
    private String address =" ";
    private String city =" ";
    private String price =" ";
    private String area =" ";
    private String info =" ";
    private String imagename;
    private String id="image";
    private String Formtype =" ";
    private String identity =" ";

    public String getSubdescription(){
        return this.subdescription;
    }
    public String getDescription() {
        return this.description;
    }
    public String getLocation(){
        return this.location;
    }
    public String getAddress(){
        return this.address;
    }
    public String getCity(){
        return this.city;
    }
    public String getPhone(){
        return this.phone;
    }
    public String getPrice(){
        return this.price;
    }
    public String getArea(){
        return this.area;
    }
    public String getInfo(){
        return this.info;
    }
    public String getNamee(){
        return this.name;
    }
    public String getImagename(){
        return this.imagename;
    }
    public String getId(){
        return this.id;
    }
    public String getFormtye(){
        return this.Formtype;
    }
    public String getIdentity(){
        return this.identity;
    }
    public void setIdentity(String desc) {
        this.identity = desc;
    }
    public void setFormtype(String desc) {
        this.Formtype = desc;
    }
    public void setLocation(String desc) {
        this.location = desc;
    }
    public void setDescription(String desc) {
        this.description = desc;
    }
    public void setSubdescription(String desc) {
        this.subdescription = desc;
    }
    public void setName(String desc) {
        this.name = desc;
    }
    public void setInfo(String desc) {
        this.info = desc;
    }
    public void setPhone(String desc) {
        this.phone = desc;
    }
    public void setPrice(String desc) {
        this.price = desc;
    }
    public void setAddress(String desc) {
        this.address = desc;
    }
    public void setArea(String desc) {
        this.area = desc;
    }
    public void setCity(String desc) {
        this.city = desc;
    }
    public void setImagename(String desc) {
        this.imagename = desc;
    }
    public void setId(String desc) {
        this.id = desc;
    }

    @Override
    public String toString() {
        return "{ desc: " + getDescription() + ", completed: " + isCompleted()  + ", name: " + getNamee() + ", phone: " + getPhone() + ", Address: " + getAddress() + ", subdescription: " + getSubdescription() + ", location: " + getLocation()+ ", city: " + getCity()  + ", price: " + getPrice() + ", Area: " + getArea() + ", Information: " + getInfo()+ ", imagename: " + getImagename()  + ", Formtype: " + getFormtye()+ ", identity: " + getIdentity()+ "}";
    }

    public static Task fromRevision(DocumentRevision rev) {
        Task t = new Task();
        t.rev = rev;
        // this could also be done by a fancy object mapper
        Map<String, Object> map = rev.getBody().asMap();

        if(map.containsKey("type") && map.get("type").equals(Task.DOC_TYPE)) {
            t.setType((String) map.get("type"));
            t.setCompleted((Boolean) map.get("completed"));
            t.setDescription((String) map.get("description"));
            t.setName((String) map.get("name"));
            t.setPhone((String) map.get("phone"));
            t.setAddress((String) map.get("address"));
            t.setSubdescription((String) map.get("subdescription"));
            t.setPrice((String) map.get("price"));
            t.setLocation((String) map.get("location"));
            t.setArea((String) map.get("area"));
            t.setInfo((String) map.get("info"));
            t.setCity((String) map.get("city"));
            t.setImagename((String) map.get("imagename"));
            t.setId((rev.getId()));
            t.setFormtype((String) map.get("Formtype"));
            t.setIdentity((String) map.get("identity"));
            System.out.println((rev.getId()) + "testttttttttttttttt");
            return t;
        }
        return null;
    }

    public Map<String, Object> asMap() {
        // this could also be done by a fancy object mapper
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        map.put("completed", completed);
        map.put("description", description);
        map.put("name", name);
        map.put("phone", phone);
        map.put("address", address);
        map.put("subdescription", subdescription);
        map.put("city", city);
        map.put("location", location);
        map.put("price", price);
        map.put("area", area);
        map.put("info", info);
        map.put("imagename",imagename);
        map.put("Formtype",Formtype);
        map.put("id",id);
        map.put("identity",identity);
        return map;
    }

}
