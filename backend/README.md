pontos finais:


**POST: investigações/{investigationId}/detectives/{detectiveId}/commands**
Permite enviar comandos.


**GET: investigações/{investigationId}/detectives/{detectiveId}/locations**
Retorna todos os locais descobertos com uma indicação de qual está atualizado.
Localização - quando está aberto (decifrar como será).
Descrição.
//TODO: Add event LocationRevealed - adiciona a locais conhecidos


**GET: investigações/{investigationId}/detectives/{detectiveId}/characters**
Retorna pessoas que o detetive conhece. Descrição se estiverem vivos.


**GET: investigações/{investigationId}/detectives/{detectiveId}/characters-locations**
Retorna as localizações das pessoas que o detetive conhece (desconhecidas - se você não souber onde elas estão).
Pense se isso fará ou não parte dos personagens. Que tal GraphQL aqui!?


**GET: investigações/{investigationId}/detectives/{detectiveId}/characters-phones**
Retorna pessoas que podem ser chamadas de qualquer lugar. Normalmente são, por exemplo, técnicos da polícia.


**GET: investigações/{investigationId}/detectives/{detectiveId}/items**
Retorna itens que o detetive conhece.
Com informações se ele os encontrou e onde.
Quem os possui, se alguém souber. Talvez outro detetive no multiplayer?



**GET: investigações/{investigationId}/detectives/{detectiveId}/commands**
Todos os comandos que o jogador executou com o resultado.


VERSÃO SIMPLES,
ENDPOINTS:

investigações/{investigationId} - estado de investigação baseado em eventos em JSON!



// EVENTO:
ID DO CENÁRIO, ID DA INVESTIGAÇÃO, etc.
