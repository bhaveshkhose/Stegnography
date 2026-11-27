package com.example.stegnography.exception;

public class InvalidImageException extends RuntimeException{

        public InvalidImageException(String msg){
            super(msg);
        }
}

