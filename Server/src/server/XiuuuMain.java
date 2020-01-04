/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package server;

/**
 *
 * @author a40284
 */
public class XiuuuMain {
    
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        
        boolean newclient = true;
        int serverDoor = -1, clientDoor = -1;
        String username = "", serverIP = "";
        
        
        // Começar por pedir em que modo a aplicação vai correr
        int choice = -1;
        do {
            choice = mainmenu();
            switch(choice) {
                
                case 1: // Querer entrar como servidor
                    System.out.println("Abrir qual porta para comunicação?");
                    serverDoor = Read.readInt();
                    //Eventualmente invocar construtor que aceita um int que é uma porta
                    System.out.println("Servidor está a correr silenciosamente...");
                    break;
                    
                case 2: // Querer entrar como cliente
                    if(newclient) {
                        int enlistcheck = -1;
                        
                        do {
                            System.out.println("Antes de começar a sussurrar, temos alguma informação por validar");
                            System.out.println("Qual vai ser o teu nome de utilizador?");
                            username = Read.readString();
                            
                            System.out.println("Qual o IP do servidor que te vais conectar?");
                            serverIP = Read.readString();
                            
                            System.out.println("Qual a porta do servidor a que te queres conectar?");
                            serverDoor = Read.readInt();
                            
                            System.out.println("Qual, das tuas portas, vais querer abrir para comunicação?");
                            clientDoor = Read.readInt();
                            
                            System.out.println("Nome : " + username + "\n" + "Conectar no servidor cujo IP : " + serverIP);
                            System.out.println("Porta do Servidor : " + serverDoor + "\n" + "Porta do Cliente : " + clientDoor);
                            System.out.println("\nQueres revalidar os teus dados?\n 0 - Prosseguir\n1 - Reintroduzir dados\n");
                            enlistcheck = Read.readInt();
                            
                        }while(enlistcheck < 0 || enlistcheck > 1); // sair do enlist
                        newclient = false;
                    }
                    // Eventualmente chamar o construtor do cliente normal com os 4 argumentos anterioes
                    // e tentar estabelecer comunicação com o servidor
                    // Cliente currClient = new Cliente(username, serverIP, serverDoor, clientDoor);
                    
                    // Talvez declarar Clientes logo debaixo da main?
                    
                    int clientChoice1;
                    do {
                        clientChoice1 = clientOptions();
                        switch(clientChoice1) { //Entrar em que modo de cliente ativo ou passivo, ou fazer pbkdf2
                            
                            case 1: // Cliente Ativo
                                // ClienteAtivo activeClient = new ClienteAtivo(currCliente);
                                // Comunicar com o servidor que queres uma lista de users à espera de ser sussurrados
                                System.out.println("\nPara quem queres sussurrar?");
                                String whisperTo = Read.readString();
                                
                                /*if(listaUsers.contains(whisperTo)) {
                                // Invocar método boolean capaz de estabelecer comunicação de 2 clientes
                                //if(activeClient.tryClient(whisperTo, porta
                                }*/
                                
                                //Dentro do if
                                int clientChoice2 = -1;
                                do {
                                    clientChoice2 = activeClientOptions();
                                    
                                    // Enviar ao cliente passivo clientChoice2
                                    
                                    switch(clientChoice2) {
                                        case 1: // Diffie Hellman
                                            // Encadeamento para fazer DiffieHellman
                                            // Queremos trocar uma chave secreta
                                            System.out.println("O que queres sussurrar?");
                                            break;
                                            
                                        case 2: // Puzzles de Merkle
                                            // Encadeamento para fazer os Puzzles de Merkle
                                            // Queremos trocar uma chave secreta
                                            System.out.println("O que queres sussurrar?");
                                            break;
                                            
                                        case 3: //RSA
                                            //Encadeamento para fazer RSA
                                            // Queremos trocar uma chave secreta
                                            System.out.println("O que queres sussurrar?");
                                            break;
                                            
                                        case 4: // Servidor fornecer chaves
                                            // Encadeamento para o servidor fornecer chaves
                                            // Queremos trocar uma chave secreta
                                            System.out.println("O que queres sussurrar?");
                                            break;
                                            
                                        case 5: // Ser um Agente de Confiança
                                            // Servir de AC
                                            // Queremos trocar uma chave secreta
                                            System.out.println("O que queres sussurrar?");
                                            break;
                                            
                                        case 0:
                                            break;
                                    }// fim do 3º switch
                                }while(clientChoice2 < 0 || clientChoice2 > 5);// Fim do 3º while
                                
                                break;
                            case 2: // Cliente Passivo
                                
                                // Correr numa thread nova?
                                // Espera de sinalização para começar comunicação
                                // ClientePassivo passiveClient = new ClientePassivo(currClient);
                                // passiveClient.run()
                                // Estabelecer e estruturar métodos dentro do ClientePassivo
                                
                                
                                break;
                            case 3: // Fazer PBKDF2
                                
                                System.out.println("String que vai servir de password :");
                                String passwd = Read.readString();
                                
                                PBKDF2 alg = new PBKDF2();
                                
                                System.out.println("Qual a mensagem que se vai cifrar?");
                                String msg = Read.readString();
                                // É preciso obter uma chave
                                // é preciso cifrar depois a mensagem com a chave
                                
                                break;
                            case 0:
                                // Nao esquecer de cortar as ligações com o servidor ou cliente
                                break;
                                
                        }// Fim do 2º switch
                    }while(clientChoice1 < 0 || clientChoice1 > 3);// Fim do 2º while
                    
                    break;
                    
                case 0: // Sair
                    System.out.println("xa..uu..u..");
                    break;
            }// fim do 1º switch
        }while(choice < 0 || choice > 2); // 1º do while ciclo
    }// Fim do main
    
    
    
    public static int mainmenu() {
        
        int choice = -1;
        
        do {
            System.out.println("Bem-vindo ao XIUUU, su..ssu..rrar.. para falar");
            System.out.println("Este dispositivo vai correr em que modo?\n");
            System.out.println("1 - Servidor");
            System.out.println("2 - Cliente\n");
            System.out.println("0 - Sair");
            choice = Read.readInt();
            
        }while(choice < 0 || choice > 2);
        
        return choice;
    }
    
    public static int clientOptions() {
        
        int choice = -1;
        
        do {
            System.out.println("Agora que és um cliente, o que queres fazer?\n");
            System.out.println("1 - Tentar sussurrar alguém (Cliente Ativo)");
            System.out.println("2 - Esperar que alguém me sussurre para sussurrar de volta (Cliente Passivo, timeout depois de 1 minuto ao nao encontrar parceiro)");
            System.out.println("3 - Experimentar gerar um segredo criptográfico usando o PBKDF2\n");
            System.out.println("0 - Voltar");
            
            choice = Read.readInt();
        }while(choice < 0 || choice > 3);
        
        return choice;
    }
    
    public static int activeClientOptions() {
        
        int choice = -1;
        
        do {
            System.out.println("Queres gerar um segredo criptográfico e comunicar de que forma?\n");
            System.out.println("1 - Trocar uma chave secreta usando Diffie-Hellman e comunicar.");
            System.out.println("2 - Trocar uma chave secreta usando Puzzles de Merkle e comunicar.");
            System.out.println("3 - Trocar uma chave secreta usando RSA e comunicar.");
            System.out.println("4 - Trocar uma chave secreta usando chaves geradas pelo servidor.");
            System.out.println("5 - Trocar uma chave secreta usando-me como um agente de confiaça.\n");
            System.out.println("0 - Voltar");
            choice = Read.readInt();
        }while(choice < 0 || choice > 5);
        
        return choice;
    }
    
    
    
    
}// Fim da classe

