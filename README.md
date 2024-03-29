# ProjetoSI


## Básico
### Relatório
https://www.overleaf.com/6568297189qzrrpbpskvcp
### Organização
- [x] Lista
- [ ] Distribuição
- [ ] Entregua de cada trabalho
### Gestão de passwords [Margarida]
- [ ] Password Based Key Derivation Function 2 (PBKDF2)
### Troca de segredos [Henrique]
- [ ] protocolo de acordo de chaves DiffieHellman
- [ ] Puzzles de Merkle
- [ ] RSA (Rivest, Shamir e Adleman)
### Distribuição [David]
- [ ] distribuição de novas chaves geradas com base em chaves pré-distribuídas
- [ ] definir um agente de confiança para distribuição de novas chave (configurar para uma das instâncias ser o agente, ex: o dono do "chat")
### Certezas
- [ ] imutabilidade (o segredo é igual dos dois lados)
- [ ] segurança (fiável, seguro, secreto)
### Métodos <Base (espero que esteja feita)>
  Se não estiver feita, cada um adapta à sua necessidade e no final,
  alguém termine isso
- [ ] Client Line Interface (CLI) OU
- [ ] Graphical User Interface (GUI)
### Modos
- [ ] Cliente [Tiago]
- - [ ] Pedir o IP
- - [ ] Pedir a porta de destino
- [ ] Servidor [Jorge]
- - [ ] Informar o IP do servidor
- - [ ] Informar a porta que fica a ouvir
- - [ ] lista de utilizadores disponíveis
- - [ ] fazer ligação entre eles

## Mais pontos
### Funcionalidades
- [ ] certificados digitais X.509 no RSA [Henrique]
- [ ] infraestrutura de chave pública para o sistema e validar cadeias de certificados no RSA [Henrique]
- [ ] fornecer certificados digitais a utilizadores [David] 
- [ ] implementar mecanismos de assinatura digital para verificação de integridade em trocas de chave efémeras no Diffie-Hellman [Henrique]
- [ ] help bastante completo e ser de simples utilização [quem for o primeiro a terminar tudo]
### Opções
- [ ] diferentes algoritmos de cifra para os Puzzles de Merkle [Henrique]
- [ ] diferentes funções de hash para o PBKDF2 [Margarida]
## No final
- [ ] Descobrir falhas
- [ ] Descrever no relatório
## Relatório
- [ ] Relatorio [quem terminar começam a fazer o relatório]
## Enunciado
O objetivo principal deste trabalho é implementar um sistema que permita `trocar segredos
criptográficos entre duas entidades de uma forma fiável e segura`.
A ideia é que o sistema integre e disponibilize uma série de esquemas de distribuição e protocolos
de acordo de chaves criptográficas, e também formas de as gerar a partir de palavras-passe.
Em princípio, `o sistema deve poder ser concretizado numa única aplicação` (talvez que o user seja cliente e server)
que, depois de executada em dois computadores ou dispositivos distintos, permita a geração e troca de
segredos entre ambas as instâncias. Entre outras a pensar, o sistema deve fornecer as seguintes funcionalidades base:

- geração de um segredo criptográfico a partir de palavras-passe inseridas pelo utilizador, 
nomeadamente através de algoritmos como o Password Based Key Derivation
Function 2 (PBKDF2);
- troca de um segredo criptográfico usando o protocolo de acordo de chaves DiffieHellman;
- troca de um segredo criptográfico usando Puzzles de Merkle;
- troca de um segredo criptográfico usando o Rivest, Shamir e Adleman (RSA);
- distribuição de novas chaves de cifra a partir de chaves pré-distribuídas;
- distribuição de novas chaves de cifra usando um agente de confiança (neste caso, a
aplicação desenvolvida deve permitir que uma das instâncias possa ser configurada
como agente de confiança);
- implementar forma de ter a certeza de que o segredo partilhado é o mesmo dos dois
lados.

A aplicação desenvolvida pode funcionar em modo `Client Line Interface (CLI)` ou fornecer
uma Graphical User Interface (GUI). Eventualmente, este sistema pode ser implementado
para dispositivos móveis, nomeadamente para a plataforma Android. Conforme já sugerido em cima, a aplicação deve poder funcionar em `modo cliente ou servidor` sendo que,
idealmente, deve deixar que seja o `utilizador a escolher o modo` sempre que é iniciada.
Caso o modo escolhido seja o de `servidor`, deve ser então `pedida o número da porta` em
que vai ficar à escuta; `caso contrário`, deve ser pedido o endereço `Internet Protocol (IP)`
e `porta do destino`. Uma aplicação que esteja a funcionar como servidor deve ser capaz
de `fornecer uma lista de utilizadores disponíveis` e facultar uma forma de se `iniciarem ligações dedicadas entre quaisquer dois utilizadores` para posterior troca de segredos. 
O trabalho e conhecimento podem ser fortalecidos através da implementação das seguintes funcionalidades:
- usar certificados digitais X.509 nas trocas de segredos que recorrem ao RSA;
- implementar uma infraestrutura de chave pública para o sistema e validar cadeias de
certificados nas trocas de segredos que recorrem ao RSA (e.g., definir um certificado
raiz para o sistema e que já vem embutido no código ou com a aplicação, gerando
depois certificados digitais para cada um dos utilizadores do sistema);
- pensar numa forma correta de fornecer certificados digitais a utilizadores;
- implementar mecanismos de assinatura digital para verificação de integridade em trocas de chave efémeras usando o Diffie-Hellman;
- possibilitar a escolha de diferentes algoritmos de cifra para os Puzzles de Merkle;
- possibilitar a escolha de diferentes funções de hash para o PBKDF2;
- ter um help bastante completo e ser de simples utilização.
Pensem numa forma de atacar o sistema (uma falha da sua implementação) e dediquemlhe uma secção no relatório. Notem que, para efeitos de avaliação e prototipagem, o
sistema desenvolvido pode executar localmente todos os seus componentes/aplicações/-
programas, desde que simule ou concretize a arquitetura sugerida (i.e., não precisa necessariamente executar em rede).
