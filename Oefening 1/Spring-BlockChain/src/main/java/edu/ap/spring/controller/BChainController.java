package edu.ap.spring.controller;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import edu.ap.spring.service.BChainInit;
import edu.ap.spring.service.Wallet;

@Controller
public class BChainController {

    @Autowired
    private BChainInit bChain;



    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @PostMapping("/")
    public String getIndex(Model model) {
        return "index";
    }

    @PostMapping("/init")
    public String init(Model model){
        bChain.init();
        return "index";
    }

    @PostMapping("/balance")
    public String balance(@RequestParam("walletKey") String key, Model model) {
        Wallet wallet = bChain.getWalletFromKey(key);
        float balance = wallet.getBalance();
        model.addAttribute("balance",balance);
        return "balance";
    }

    @PostMapping("/sendFunds")
    public String sendFunds(@RequestParam("from") String from, @RequestParam("to") String to,
            @RequestParam("amount") Float amount) {
        Wallet senderWallet = bChain.getWalletFromKey(from);
        Wallet receiverWallet = bChain.getWalletFromKey(to);
        try{
            bChain.block1.addTransaction(senderWallet.sendFunds(receiverWallet.getPublicKey(), amount), bChain.bChain);
        }catch(Exception e){}
        bChain.bChain.addBlock(bChain.block1);
        return "redirect:/";
    }
}