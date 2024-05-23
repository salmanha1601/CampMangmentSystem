Camping Mangment System 

Installion: 
  - download JAR 22 and SDK 22.0
  - download intellji or eclips
  - video's that can help in the download process:
    Eclips: https://www.youtube.com/watch?v=_7OM-cMYWbQ&list=PLZPZq0r_RZOM-8vJA3NQFZB7JroDcMwev&ab_channel=BroCode
    Intelji: https://www.youtube.com/watch?v=Ope4icw6bVk&list=PLZPZq0r_RZOM-8vJA3NQFZB7JroDcMwev&index=2&ab_channel=BroCode

Database:
  - Download MySQL database
  - use the following instruction to build all the neccesries tables:
     CREATE TABLE `activities` (
          `activity_id` int NOT NULL AUTO_INCREMENT,
          `instructor_id` varchar(45) DEFAULT NULL,
          `group_id` varchar(45) DEFAULT NULL,
          `date` varchar(45) DEFAULT NULL,
          `description` varchar(45) DEFAULT NULL,
          PRIMARY KEY (`activity_id`)
        );
    CREATE TABLE `campers` (
        `camper_id` varchar(45) NOT NULL,
        `parent_id` varchar(45) DEFAULT NULL,
        `name` varchar(45) DEFAULT NULL,
        `phone` varchar(45) DEFAULT NULL,
        `camp_id` varchar(45) DEFAULT NULL,
        `group_id` varchar(45) DEFAULT NULL,
        PRIMARY KEY (`camper_id`)
    );
    CREATE TABLE `camps` (
        `camp_id` varchar(45) NOT NULL,
        `owner_id` varchar(45) NOT NULL,
        PRIMARY KEY (`camp_id`)
      );
    CREATE TABLE `feedback` (
        `feedback_id` int NOT NULL AUTO_INCREMENT,
        `user_id` varchar(45) DEFAULT NULL,
        `camp_id` varchar(45) DEFAULT NULL,
        `feedback_content` varchar(100) DEFAULT NULL,
        PRIMARY KEY (`feedback_id`)
      );
    CREATE TABLE `groups` (
        `group_id` varchar(45) NOT NULL,
        `group_name` varchar(45) DEFAULT NULL,
        `min_age` varchar(45) DEFAULT NULL,
        `max_age` varchar(45) DEFAULT NULL,
        `camp_id` varchar(45) NOT NULL,
        PRIMARY KEY (`group_id`,`camp_id`)
      );
    CREATE TABLE `instructors` (
        `id` varchar(45) NOT NULL,
        `name` varchar(45) DEFAULT NULL,
        `password` varchar(45) DEFAULT NULL,
        `group_id` varchar(45) NOT NULL,
        `bank_details` int DEFAULT NULL,
        PRIMARY KEY (`id`,`group_id`)
      );
    CREATE TABLE `payments` (
        `amount` int DEFAULT NULL,
        `camp_id` varchar(45) DEFAULT NULL,
        `payment_id` int NOT NULL AUTO_INCREMENT,
        `user_id` varchar(45) DEFAULT NULL,
        PRIMARY KEY (`payment_id`)
      );
    CREATE TABLE `users` (
        `userId` varchar(45) NOT NULL,
        `password` varchar(45) DEFAULT NULL,
        `name` varchar(45) DEFAULT NULL,
        `phone` varchar(45) DEFAULT NULL,
        `email` varchar(45) DEFAULT NULL,
        PRIMARY KEY (`userId`)
      );
    
