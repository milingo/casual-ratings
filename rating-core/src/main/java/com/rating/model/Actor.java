package com.rating.model;

//"character": "Darth Vader",
//"id": 24342,
//"name": "David Prowse",
//"order": 16,
//"profile_path": "/a2RoHYMSiRqV6hXL6Z5CXtNyDkt.jpg"

/**
 * 
 * @author miguel
 *
 */
public class Actor {

        private String character;
        private String id;
        private String name;
        private int order;
        private String profile_path;
        
        public String getCharacter() {
            return character;
        }
        
        public void setCharacter(String character) {
            this.character = character;
        }
        
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public int getOrder() {
            return order;
        }
        
        public void setOrder(int order) {
            this.order = order;
        }
        
        public String getProfile_path() {
            return profile_path;
        }
        
        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }

}
