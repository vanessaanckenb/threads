In progress...

<br/>
<br/>

# Threads

<br/>
<br/>

"Um fluxo de controle sequencial isolado dentro de um programa"

Através das Threads podemos executar tarefas em paralelo;

Uma classe que implementa a interface Runnable define a tarefa que o Thread executará;

O construtor da classe Thread recebe esse Runnable;

Devemos inicializar uma Thread explicitamente através do método start();

Não é possível determinar a ordem de execução, que pode ser sempre diferente inclusive na mesma máquina, isso depende puramente do escalonador de threads (scheduler). É ele que agenda a execução da thread e define quanto tempo cada thread pode executar.

Nós não controlamos essas escolhas (embora possamos dar "dicas" ao escalonador). Por isso que nunca sabemos ao certo a ordem em que programas paralelos são executados.

a JVM mapeia as threads java para threads nativas do sistema

JVM -> Sistema Operacional -> Hardware

A ordem de execução depende da JVM e não do nosso código

Thread.sleep(millis) podemos mandar uma thread dormir

<br/>
<br/>
Estados de uma thread:

NEW Uma thread foi criada mas ainda não foi iniciada.

RUNNABLE A thread está rodando dentro da JVM.

BLOCKED A thread foi bloqueada pois não conseguiu pegar a chave.

WAITING A thread está esperando pois foi chamado this.wait().

TIMED_WAITING A thread está esperando pois foi chamado this.wait(milis).

TERMINATED A thread está finalizada.


<br/>
<br/>

---------------------------------------------------------------

<br/>

* Exemplo A - Trabalhando com threads implementando Runnable em uma classe separada com variáveis

A thread implementa a interface funcional Runnable.

ao darmos new em uma thread devemos passar no parametro uma classe que implemente a interface Runnable

basta criar uma classe que implemente a interface Runnable e dar override no método run

no método run passaremos o comando que desejamos.


<br/>
<br/>

---------------------------------------------------------------

<br/>


* Exemplo B - Trabalhando com threads implementando Runnable em uma classe separada sem variáveis
```
new Thread(new PrintNumbersImplementingRunnableB()).start();
new Thread(new PrintNumbersImplementingRunnableB()).start();
new Thread(new PrintNumbersImplementingRunnableB()).start();
```


<br/>
<br/>

---------------------------------------------------------------

<br/>


* Exemplo C - Trabalhando com threads usando o Runnable atraves de um Lambda


Em vez de criarmos uma classe, podemos usar um lambda, já que o compilador "sabe" que o parametro a ser passado deve implementar Runnable, que é uma interface funcional (com apenas um método abstrato). Para entender melhor sobre o lambda, veja https://github.com/vanessaanckenb/java8-code-evolution


<br/>
<br/>

---------------------------------------------------------------

<br/>


* Exemplo D - Trabalhando com threads extendendo Thread

Apesar de ser funcional, essa forma de criar uma thread é considerada um mau exemplo de herança, cada classe deve ter sua responsabilidade, apenas para exemplos didaticos

A classe Thread já implementa a interface Runnable, logo a Thread também é um Runnable

Sabendo disso, você pode criar uma subclasse dela e sobrescrever o método run

Crie um classe que extends de Thread

sobreescreva o método run

instancie a classe no main

e ela podera usar os métodos da classe mãe como o .start


<br/>
<br/>

---------------------------------------------------------------

<br/>


* Exemplo E - Trabalhando com threads usando o synchronized

```
ClassWithSynchronizedModifer classWithSynchronizedModifer = new ClassWithSynchronizedModifer();
new Thread(new ClassThatImplementsRunnable(classWithSynchronizedModifer), "Thread Name to see in Logs");
``` 

synchronized é o modificador que deve ser colocado em um método para que não possa ser executado por mais de uma Thread ao mesmo tempo, significa que bloqueamos o objeto para outros threads. A sincronização é feita através de mutex, que nada mais é do que a chave do objeto.
``` 
synchronized (objeto) {
     //esse bloco pode ser executado por uma thread apenas
}
``` 

Operação atômica é quando a execução não pode ser interrompida na metade.

Todo o bloco synchronized será executado de uma vez só, de maneira atômica. 

Até pode ser que o escalonador para a thread no meio, para alguma outra thread executar uma outra coisa, mas nenhuma outra thread pode entrar nesse bloco enquanto aquela com a chave não sai.

Exemplo: em transações de banco de dados. Transações possuem as caraterísticas ACID que vão além da funcionalidade syncronized:

A de Atômico (é isso que syncronized faz)

C de Consistente

I de Isolado

D de Durável

<br/>

object.wait()

ao chamar object.wait() a thread fica no estado de espera

estado de espera significa WAITING no mundo de threads

uma thread esperando pode ser notificada pelo método object.notifyAll()

wait() e notify() sempre devem ser chamados dentro de um bloco synchronized;

Temos que ter cuidado para não mandar um thread esperar quando não há necessidade pois a JVM nunca termina se há threads esperando

é preciso acordar/notificar as threads para continuar executar o programa. 
 
Os método wait() e notify() vem da classe Object. Todos os objetos possuem esses métodos.
 
método wait() tem que ser chamado dentro de um método ou bloco synchronized
 
Quando chamamos wait() a thread devolve a chave e fica aguardando. No vocabulário do mundo de threads, essa condição também é chamado de guarded block. Em outras palavras, a thread fica bloqueada aguardando um sinal/notificaço
 
<br/>

Daemon threads são interrompidas quando a thread principal, aquela que executa o método main, termina de executar e o programa termina.

Uma thread daemon é uma prestadora de serviços para outras threads. Ela só é usada enquanto as outras threads estão rodando.
 
Uma thread daemon não impede a JVM de terminar desde que não existem mais threads principais em execução. Um exemplo de uma thread daemon é o coletor de lixo da JVM (Garbage Collector)

``` 
Thread thread = new Thread(runnable);
thread.setDaemon(true);
thread.start();
```

Na classe Thread existe um método setPriority

A prioridade é um valor inteiro entre 1 e 10, sendo 10 a prioridade mais alta.

``` 
Thread limpeza = new Thread(new TarefaLimpeza(banheiro), "Limpeza");
limpeza.setPriority(10);
limpeza.start();
``` 

ou também usar as constantes:

``` 
limpeza.setPriority(Thread.MIN_PRIORITY);
limpeza.setPriority(NORM_PRIORITY);

``` 

se não usamos nenhuma prioridade explícita, a thread vai assumir o valor 5 (NORM_PRIORITY) para a prioridade.


<br/>
<br/>

---------------------------------------------------------------

<br/>


* Exemplo F - Mostrando concorrência adicionando itens em um array sem o modificador synchronized


Ao rodar o code, vê-se no console varios elementos da lista com null.
O que acontece?


As vezes quando uma thread entra no objeto, ela pode ter adicionado o item na lista e antes de mudar o índice outra thread entra e sobreescreve o item no mesmo indice.

a outra thread aumenta o índice e depois essa thread também aumenta o índice, logo um índice é sobreescrito, outro é pulado.

como resolver isso? usando o synchronized, como no exemplo abaixo.

<br/>
<br/>

---------------------------------------------------------------

<br/>

* Exemplo G - Mostrando concorrência adicionando itens em um array com o modificador synchronized

``` 
public synchronized void metodo() {...}
``` 
garante que todo o código dentro do método será executado de maneira atômica, de uma vez só. 

Uma thread não pode parar na primeira linha, sem ter incrementado o índice. 

Se isso acontece, uma outra thread poderá executar esse código e adicionar um elemento na mesma posição.

Esse problema não só acontece nos métodos que modificam os dados. Isso também se aplica aos métodos acessores, get por exemplo. Eles também devem usar synchronized.

<br/>
<br/>

---------------------------------------------------------------

<br/>

* Exemplo H - Usando ArrayList (não é Thread-safe)

Se mudarmos o array para um ArrayList o synchronized não funcionará, pois, de acordo com a documentacao, o ArrayList não é thread-safe.

Para usar o ArrayList com o synchronized:
``` 
List<String> lista = Collections.synchronizedList(new ArrayList<>());
``` 

<br/>
<br/>

---------------------------------------------------------------

<br/>

* Exemplo I - Usando Vector (tread-safe)

java.util.Vector

É thread-safe = significa que o código funciona corretamente mesmo com vários threads compartilhando o objeto;

<br/>
<br/>

---------------------------------------------------------------






