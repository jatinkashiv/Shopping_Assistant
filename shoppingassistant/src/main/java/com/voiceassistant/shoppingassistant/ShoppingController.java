package com.voiceassistant.shoppingassistant;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ShoppingController
{

    @Autowired
    private ItemRepo repo;

    @PostMapping("/voice-command")
    public Item handleVoiceCommand(@RequestBody VoiceCommandRequest request) {
        String command = request.getCommand();
        Item item = parseCommand(command);
        return repo.save(item);
    }

    @GetMapping("/items")
    public List<Item> getAll() {
        return repo.findAll();
    }

    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable Long id) {
        repo.deleteById(id);
    }

    // Basic NLP logic here
    private Item parseCommand(String command) {
        Map<String, Integer> wordToNumber = Map.of(
            "one", 1, "two", 2, "three", 3, "four", 4, "five", 5,
            "six", 6, "seven", 7, "eight", 8, "nine", 9, "ten", 10
        );
        command = command.trim().toLowerCase();

    // 1. Full command pattern with quantity + unit + name
    Pattern pattern = Pattern.compile(
        "(?i)(add|buy|need)\\s+(\\d+|one|two|three|four|five|six|seven|eight|nine|ten)\\s+(\\w+)\\s+(?:of\\s+)?(.+)"
    );

    Matcher matcher = pattern.matcher(command);
    if (matcher.find()) {
        String quantityStr = matcher.group(2).toLowerCase();
        int quantity = wordToNumber.getOrDefault(quantityStr, -1);
        if (quantity == -1) {
            try {
                quantity = Integer.parseInt(quantityStr);
            } catch (NumberFormatException e) {
                quantity = 1;
            }
        }

        String unit = matcher.group(3).toLowerCase();
        String name = matcher.group(4).toLowerCase().trim();

        return new Item(null, name, "general", quantity, unit);
    }

    // 2. Pattern: just "add milk" or "buy bread"
    Pattern simplePattern = Pattern.compile("(?i)(add|buy|need)\\s+(.+)");
    Matcher simpleMatcher = simplePattern.matcher(command);
    if (simpleMatcher.find()) {
        String name = simpleMatcher.group(2).toLowerCase().trim();
        return new Item(null, name, "general", 1, "unit");
    }

    // 3. Fallback
    return new Item(null, command.toLowerCase(), "general", 1, "unit");
}
    
}

@Repository
interface ItemRepo extends JpaRepository<Item, Long> {}
