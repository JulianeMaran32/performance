# Flowers

Esta atividade tem como objetivo buscar o entendimento da criação de múltiplas threads e seus benefícios em um
problema de processamento de imagens.  
Utilize o código fonte que resolve o problema "single-thread": `Main.class`

Deseja-se pintar todas as flores brancas de roxas.

## Exercício Prático:

Crie agora um método para executar o processamento da imagem multi-threaded.   
Faça um código que permita criar quantas threads forem desejadas.

1. Meça o tempo para executar o programa sem thread.
2. Agora meça o tempo utilizando somente uma thread. O tempo é maior ou menor? Explique.
3. Agora meça o tempo utilizando duas threads e quatro threads. Explique os tempos, foram melhores?
4. Faça várias medidas e gere um gráfico relacionando o número de threads com o tempo. Apresente aqui o gráfico e
   explique a relação com o hardware do seu computador.
5. Agora fixe o número de trheads em 4 ou 6 e gere um gráfico variando o tamanho da imagem (diminuindo). Para isso
   calcule o speed-up (em relação a execução single-thread). O gráfico será de speed-up X resolução.

## Exercício Teórico:

Pesquise o que são deadlocks, demonstre sua resposta com um exemplo.  
Explique também as condições para acontecerem deadlocks e possíveis soluções
