-- V1__initial_db_setup.sql
-- Initial database schema for ReviewService
-- Generated from JPA entities

-- =====================================================
-- BASE TABLES (no foreign key dependencies)
-- =====================================================

-- Driver table
CREATE TABLE driver (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    license_number VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE KEY UK_license_number (license_number)
) ENGINE=InnoDB;

-- Passenger table
CREATE TABLE passenger (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Student table
CREATE TABLE student (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    name VARCHAR(255),
    roll_no VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Course table
CREATE TABLE course (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Booking Review table (base review table)
CREATE TABLE booking_review (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    rating FLOAT(53),
    content VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- =====================================================
-- TABLES WITH FOREIGN KEYS
-- =====================================================

-- Booking table
CREATE TABLE booking (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    driver_id BIGINT,
    passenger_id BIGINT,
    review_id BIGINT,
    start_time DATETIME(6),
    end_time DATETIME(6),
    total_distance BIGINT NOT NULL,
    booking_status ENUM('ASSIGNING_DRIVER', 'CAB_ARRIVED', 'CANCELED', 'COMPLETED', 'IN_RIDE', 'SCHEDULE'),
    PRIMARY KEY (id),
    UNIQUE KEY UK_review (review_id)
) ENGINE=InnoDB;

-- Driver Review table (extends booking_review)
CREATE TABLE driver_review (
    driver_review_id BIGINT NOT NULL,
    driver_review_comment VARCHAR(255) NOT NULL,
    PRIMARY KEY (driver_review_id)
) ENGINE=InnoDB;

-- Passenger Review table (extends booking_review)
CREATE TABLE passenger_review (
    id BIGINT NOT NULL,
    passenger_rating VARCHAR(255) NOT NULL,
    passenger_review_content VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Course-Student join table (appears to be duplicate mapping)
CREATE TABLE course_student (
    course_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL
) ENGINE=InnoDB;

-- Course-Students join table (appears to be duplicate mapping)
CREATE TABLE course_students (
    course_id BIGINT NOT NULL,
    students_id BIGINT NOT NULL
) ENGINE=InnoDB;

-- =====================================================
-- FOREIGN KEY CONSTRAINTS
-- =====================================================

-- Booking foreign keys
ALTER TABLE booking
    ADD CONSTRAINT fk_booking_driver
    FOREIGN KEY (driver_id)
    REFERENCES driver(id);

ALTER TABLE booking
    ADD CONSTRAINT fk_booking_passenger
    FOREIGN KEY (passenger_id)
    REFERENCES passenger(id);

ALTER TABLE booking
    ADD CONSTRAINT fk_booking_review
    FOREIGN KEY (review_id)
    REFERENCES booking_review(id);

-- Course-Student join table foreign keys
ALTER TABLE course_student
    ADD CONSTRAINT fk_course_student_course
    FOREIGN KEY (course_id)
    REFERENCES course(id);

ALTER TABLE course_student
    ADD CONSTRAINT fk_course_student_student
    FOREIGN KEY (student_id)
    REFERENCES student(id);

-- Course-Students join table foreign keys
ALTER TABLE course_students
    ADD CONSTRAINT fk_course_students_student
    FOREIGN KEY (students_id)
    REFERENCES student(id);

ALTER TABLE course_students
    ADD CONSTRAINT fk_course_students_course
    FOREIGN KEY (course_id)
    REFERENCES course(id);

-- Driver Review foreign key (inheritance)
ALTER TABLE driver_review
    ADD CONSTRAINT fk_driver_review_booking_review
    FOREIGN KEY (driver_review_id)
    REFERENCES booking_review(id);

-- Passenger Review foreign key (inheritance)
ALTER TABLE passenger_review
    ADD CONSTRAINT fk_passenger_review_booking_review
    FOREIGN KEY (id)
    REFERENCES booking_review(id);

-- =====================================================
-- INDEXES FOR PERFORMANCE
-- =====================================================

-- Indexes on foreign keys
CREATE INDEX idx_booking_driver ON booking(driver_id);
CREATE INDEX idx_booking_passenger ON booking(passenger_id);
CREATE INDEX idx_booking_review ON booking(review_id);
CREATE INDEX idx_booking_status ON booking(booking_status);
CREATE INDEX idx_booking_start_time ON booking(start_time);

-- Indexes on join tables
CREATE INDEX idx_course_student_course ON course_student(course_id);
CREATE INDEX idx_course_student_student ON course_student(student_id);
CREATE INDEX idx_course_students_course ON course_students(course_id);
CREATE INDEX idx_course_students_student ON course_students(students_id);

-- Index on review rating for queries
CREATE INDEX idx_booking_review_rating ON booking_review(rating);