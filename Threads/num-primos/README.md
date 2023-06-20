| Máquina | CPI médio | Tempo de Execução | MIPS   | Desempenho | Speed-up     |
|:--------|:----------|:------------------|:-------|:-----------|:-------------|
| A       | 2,75      | 0,115 segundos    | 69,565 | 8,696      | menos rápida |
| B       | 1,95      | 0,1125 segundos   | 89,333 | 8,889      | mais rápida  |

---

# INTRODUÇÃO A THREADS EM JAVA

> Em Java todas as propriedades e métodos estão encapsulados no corpo JDK. Assim, para criarmos uma thread instanciamos
> um objeto do tipo `Thread`. Passamos um objeto da classe que implementa a interface `Runnable`. O que inserirmos no
> método `run` será executado (desde que a trhead seja agendada pelo S.O.).

```java
public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // código que irá rodar em uma nova thread
            }
        });
        thread.start();
    }
}
```

Replique o código. Atribua um nome a thread utilizando o método `setName()`. Ainda, atribua uma prioridade
utilizando `setPriority()`. Faça um teste utilizando a constante `Thread.MAX_PRIORITY`. Apresente o print dos seus
testes com o nome dado e a prioridade.

```java
public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Estamos na thread " + Thread.currentThread().getName());
            }
        });
        System.out.println("Estamos na thread " + Thread.currentThread().getName() + " antes de iniciar uma nova trhead");
        thread.start(); // JVM cria a thread e passa para o S.O.
        Thread.sleep(1000);
        System.out.println("Estamos na thread " + Thread.currentThread().getName() + " depois de iniciar uma nova trhead");
    }
}
```

> Uma outra maneira de implementarmos threads é criarmos uma classe e extendermos a classe Thread (que implementa
> Runnable). Agora conseguimos utilizar "this".

```java
public class Main {
    public static void main(String[] args) {
        Thread thread = new MinhaThread();
        thread.start();
    }

    private static class MinhaThread extends Thread {
        @Override
        public void run() {
            System.out.println("Olá da thread " + Thread.currentThread().getName());
        }
    }
}
```

> Crie um programa em que duas threads são criadas (t1 e t2). No caso do Java, instancie dois objetos da sua classe
> Thread (MinhaThread) em uma classe chamada Processo, por exemplo. Inicialize as duas threads e coloque um breakpoint no
> método run(). Observe e reporte quantos Threads existem.

---

## Exercício:  

Faça um programa que dado um intervalo (numérico), imprima todos os números primos existentes. 
Exemplo:  
- Intervalo: [2;10]
- Primos: 2, 3, 5, 7