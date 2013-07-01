package models.service.finance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import play.api.data.Form;
import models.data.AdsTransaction;
import models.data.Deposito;
import models.data.TransferConfirmation;
import models.data.User;
import models.data.UserContact;
import models.data.enumeration.PaymentMethodEnum;
import models.form.backendForm.financeForm.TransferForm;

public class DepositoOperator {

	SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy hh:mm");

	public TransferConfirmation saveConfirmation(Form<TransferForm> input, User user){
		try {
			TransferConfirmation confirmation=new TransferConfirmation();
			confirmation.setAmount(Integer.parseInt(input.get().amount));
			confirmation.setDescription(input.get().description);
			if(input.get().senderBankAccount.length()<5){
				try{
					Integer idContact=Integer.parseInt(input.get().senderBankAccount);
					UserContact contact=UserContact.find.byId(idContact);
					confirmation.setSenderBankAccount(contact.getContact_value());
				}catch(Exception e){
					confirmation.setSenderBankAccount(input.get().senderBankAccount);
					e.printStackTrace();
				}
				
			}
			confirmation.setTimestamp_created(new Date());
			confirmation.setTransfer_date(format.parse(input.get().transfer_date));
			confirmation.setUser(user);
			confirmation.save();
			
			return confirmation;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public TransferConfirmation updateConfirmation(Form<TransferForm> form, TransferConfirmation transfer){
		return null;
	}
	public boolean validate(TransferConfirmation transfer){
		Deposito deposito=new Deposito();
		User user=transfer.getUser();
		int current_balance=user.getCurrent_balance();
		deposito.setAmount(transfer.getAmount());
		deposito.setCurrent_balance(current_balance+transfer.getAmount());
		deposito.setPayment_method(PaymentMethodEnum.TRANSFER);
		deposito.setDescription(transfer.getDescription());

		return true;
	}
	public boolean deleteTransfer(TransferConfirmation transfer){
		try {
			transfer.delete();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
