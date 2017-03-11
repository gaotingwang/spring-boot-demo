package com.gtw.test.web;

import com.gtw.test.entity.Seckill;
import com.gtw.test.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
    private final SeckillService seckillService;

    @Autowired
    public SeckillController(SeckillService seckillService) {
        this.seckillService = seckillService;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView modelView = new ModelAndView();
        modelView.addObject("list", seckillService.getSeckillList());
        modelView.setViewName("list");

        return modelView;
    }
//    @RequestMapping(value = "/list",method = RequestMethod.GET)
//    public String list(Model model) {
//        List<Seckill> list = seckillService.getSeckillList();
//        model.addAttribute("list",list);
//        return "list";
//    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }

        Seckill seckill=seckillService.getById(seckillId);
        if (seckill == null) {
            return "forward:/seckill/list";
        }

        model.addAttribute("seckill", seckill);

        return "detail";
    }

    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Seckill exposer(@PathVariable Long seckillId) {
        return seckillService.exportSeckill(seckillId);
    }
}