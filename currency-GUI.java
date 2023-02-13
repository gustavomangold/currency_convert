/*
Programa: Uma implementacao de uma classe que modela uma GUI com conversor de moedas.
Objetivos: Criacao de uma GUI agora funcional, com ActionListener.
Entrada: N/A
Saida: N/A
Autor: Gustavo C. Mangold
Data: 02/05/2022
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

class GUI extends JFrame implements ActionListener{

    private JComboBox cbEntrada;
    private JTextField textoEntrada, textoSaida;
    private JButton botaoConverter, botaoResetar;
    private JLabel etiquetaEntrada, etiquetaSaida, etiquetaImagem;
    private JPanel painelTopo, painelEsquerda, painelCentro, painelDireita, painelBase;
    private JRadioButton rbReal, rbDolar, rbEuro;

    public GUI(String nome){ 
        super(nome);
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        textoEntrada = new JTextField(20);
        textoSaida = new JTextField(20);
        textoSaida.setEditable(false);
        
        etiquetaEntrada = new JLabel("Valor:");
        etiquetaSaida = new JLabel("Valor Convertido:");

        botaoConverter = new JButton("Converter!");
        botaoResetar = new JButton("Resetar");
        
        botaoConverter.addActionListener(this);
        botaoResetar.addActionListener(this);
        
        rbReal = new JRadioButton("Real");
        rbDolar = new JRadioButton("Dólar");
        rbEuro = new JRadioButton("Euro");
        rbReal.setSelected(true);

        ButtonGroup grupoSaida = new ButtonGroup();
        grupoSaida.add(rbReal);
        grupoSaida.add(rbDolar);
        grupoSaida.add(rbEuro);

        cbEntrada = new JComboBox(new String[] {"Real", "Dólar", "Euro"});
        
        painelTopo = new JPanel();
        painelBase = new JPanel();
        painelCentro = new JPanel();
        painelEsquerda = new JPanel();
        painelDireita = new JPanel();
        painelEsquerda.setBorder(BorderFactory.createTitledBorder("De"));
        painelEsquerda.setLayout(new FlowLayout(FlowLayout.CENTER));
        painelDireita.setBorder(BorderFactory.createTitledBorder("Para"));
        painelDireita.setLayout(new BoxLayout(painelDireita, BoxLayout.Y_AXIS));
                
        painelTopo.add(etiquetaEntrada);
        painelTopo.add(textoEntrada);
        
        painelEsquerda.add(cbEntrada);
        
        painelDireita.add(rbReal);
        painelDireita.add(rbDolar);
        painelDireita.add(rbEuro);

        painelBase.add(botaoConverter);
        painelBase.add(botaoResetar);
        painelBase.add(etiquetaSaida);
        painelBase.add(textoSaida);

        contentPane.add(painelTopo, BorderLayout.NORTH);
        contentPane.add(painelEsquerda, BorderLayout.WEST);
        contentPane.add(painelDireita, BorderLayout.EAST);
        contentPane.add(painelBase, BorderLayout.SOUTH);

        try{
           BufferedImage imagem = ImageIO.read(new File("image_conversor.png"));
           etiquetaImagem = new JLabel(new ImageIcon(imagem));
           painelCentro.add(etiquetaImagem);
        } catch (IOException e){
           System.out.println("Imagem inacessível!");
        }
        
        contentPane.add(painelCentro, BorderLayout.CENTER);

        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e){
	if (e.getActionCommand().equals("Resetar")){
		textoSaida.setText("");
		textoEntrada.setText("");
	}
	else {
	if (textoEntrada.getText().equals("")){
		return;
	}
	trataConversao();
	}
    }
    public void trataConversao(){
    
	double valor = 0;
	//exceçao para numberexception
	try{
		valor = Double.parseDouble(textoEntrada.getText());
	} catch (NumberFormatException e) {
		textoSaida.setForeground(Color.red);
		textoSaida.setText("Digite um valor válido!");
		return;
	}
	//garante que mesmo um erro ocorrendo antes, o texto fique preto na próxima tentativa
	textoSaida.setForeground(Color.black);

	//essa parte converte qualquer entrada para real, diminuindo o numero de if's
	if (cbEntrada.getSelectedItem().equals("Dólar")){
		valor = valor*4.6663991;
	}
	else if (cbEntrada.getSelectedItem().equals("Euro")){
		valor = valor*5.035977938;
	}

	//essa converte para o valor desejado conforme o botao apertado
	if (rbReal.isSelected()){
		textoSaida.setText(String.format("%.2f", valor));
	}
	else if (rbDolar.isSelected()){
		textoSaida.setText(String.format("%.2f", valor/4.6663991));
	}
	else if (rbEuro.isSelected()){
		textoSaida.setText(String.format("%.2f", valor/5.035977938));
	}
    }
}



public class TestaGUI{

   public static void main(String[] args){
       GUI gui = new GUI("Conversor de Câmbio");
   }

}
