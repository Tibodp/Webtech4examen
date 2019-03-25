package edu.ap.spring.service;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.ap.spring.transaction.Transaction;

@Component
@Scope("prototype")
public class BChainInit {
	@Autowired
	public BlockChain bChain;
	@Autowired
	private Wallet coinbase, walletA, walletB;
	private Transaction genesisTransaction;
	
	private Map<String, Wallet> map = new HashMap<String, Wallet>();
	public Block block1;

	public void init() {
		bChain.setSecurity();
		coinbase.generateKeyPair();
		walletA.generateKeyPair();
		walletB.generateKeyPair();

		//create genesis transaction, which sends 100 coins to walletA:
		genesisTransaction = new Transaction(coinbase.getPublicKey(), walletA.getPublicKey(), 100f);
		genesisTransaction.generateSignature(coinbase.getPrivateKey());	 // manually sign the genesis transaction	
		genesisTransaction.transactionId = "0"; // manually set the transaction id
		
					
		//creating and Mining Genesis block
		Block genesis = new Block();
		genesis.setPreviousHash("0");
		genesis.addTransaction(genesisTransaction, bChain);
		bChain.addBlock(genesis);

		Block block1 = new Block();
		block1.setPreviousHash(genesis.hash);
		block1.setTimeStamp();
        block1.calculateHash();


        map.put("A",walletA);
        map.put("B", walletB);
	}
	public Wallet getWalletFromKey(String wallet){
        return this.map.get(wallet);
    }
}