package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String item(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "/basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String saveV1(@RequestParam String itemName,
                         @RequestParam int price,
                         @RequestParam Integer quantity,
                         Model model) {
        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String saveV2(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String saveV3(@ModelAttribute("item") Item item) { // @ModelAttribute가 model 객체도 만들고, view에서 사용하는 model에도 추가하는 기능도 한다.
        itemRepository.save(item);
        // model.addAttribute("item", item); // @ModelAttribute가 자동으로 추가해준다.
        return "basic/item";
    }

//    @PostMapping("/add")
    public String saveV4(@ModelAttribute Item item) {
        // @ModelAttribute에 name이 없으면 클래스네임의 앞글자만 소문자로 바꿔서 model에 자동으로 추가해준다.
        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String saveV5(Item item) { // @ModelAttribute 생략 가능
        itemRepository.save(item);
        return "basic/item";
    }

    @PostMapping("/add")
    public String saveV6(Item item) { // @ModelAttribute 생략 가능
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId(); // PRG 패턴으로 Post 요청 후 새로고침 시 발생하는 문제점 수정.
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
