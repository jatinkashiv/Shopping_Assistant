package com.voiceassistant.shoppingassistant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody 
    public String home() {
        return ("<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title>Voice Command Shopping Assistant</title>" +
                "<style>" +
                "body { background-color: #343541; color: #ECECF1; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;" +
                "display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }" +

                ".chat-container { background-color: #444654; width: 480px; padding: 20px; border-radius: 12px;" +
                "box-shadow: 0px 4px 15px rgba(0,0,0,0.5); }" +

                "h1 { text-align: center; font-weight: bold; margin-bottom: 20px; font-size: 22px; }" +

                ".message { padding: 12px; border-radius: 8px; margin-bottom: 12px; font-size: 16px; line-height: 1.4; }" +

                ".assistant { background-color: #565869; }" +
                ".user { background-color: #3E3F4B; text-align: left; }" +

                "a { color: #10a37f; text-decoration: none; font-weight: bold; }" +
                "a:hover { text-decoration: underline; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='chat-container'>" +
                "<h1>Welcome to Voice Command Shopping Assistant</h1>" +
                "<div class='message assistant'>Operations:</div>" +
                "<div class='message user'><a href='/voice.html'>For voice functionality use</a></div>" +
                "<div class='message user'><a href='/api/items'>For data view (JSON)</a></div>" +
                "</div>" +
                "</body>" +
                "</html>");
    }
}