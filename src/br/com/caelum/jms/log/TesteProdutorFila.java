package br.com.caelum.jms.log;


import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutorFila {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.start();
		// abstrai o trabalho transacional e confirmação do recebimento da mensagem.
		// o primeiro parâmetro(false) define se queremos usar o tratamento da transação
		// como explícito.
		// segundo parametro serve para confirmar automaticamente o recebimento da
		// messagem JMS.
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// organizar o recebimento e a entrega das mensagens
		Destination fila = (Destination) context.lookup("LOG");
		
		
		MessageProducer producer = session.createProducer(fila);
		
		Message message = session.createTextMessage("ERROR | Apache ActiveMQ 5.16.0 (localhost, ID:DESKTOP-1IMQV9G-55121-1606915983991-0:1) is starting");
		producer.send(message, DeliveryMode.NON_PERSISTENT,9, 80000);
		
		
		/*
		 * for (int i = 0; i < 1000; i++) { 
		 * Message message = session.createTextMessage("<pedido><id>" + i + "</id></pedido>");
		 * producer.send(message); }
		 */
		

		//new Scanner(System.in).nextLine();

		session.close();
		connection.close();
		context.close();

	}

}
