import java.awt.*;
import java.util.Random;

/**
	Esta classe representa a bola usada no jogo. A classe princial do jogo (Pong)
	instancia um objeto deste tipo quando a execução é iniciada.
*/

public class Ball {

	private double cx;
	private double cy;
	private double width;
	private double height;
	private Color color;
	private double speed;
	
	private boolean positivoX;
	private boolean positivoY;
	private boolean gol;
	private boolean ladoDoPlayer;

	/**
		Construtor da classe Ball. Observe que quem invoca o construtor desta classe define a velocidade da bola 
		(em pixels por millisegundo), mas não define a direção deste movimento. A direção do movimento é determinada 
		aleatóriamente pelo construtor.

		@param cx coordenada x da posição inicial da bola (centro do retangulo que a representa).
		@param cy coordenada y da posição inicial da bola (centro do retangulo que a representa).
		@param width largura do retangulo que representa a bola.
		@param height altura do retangulo que representa a bola.
		@param color cor da bola.
		@param speed velocidade da bola (em pixels por millisegundo).
	*/

	public Ball(double cx, double cy, double width, double height, Color color, double speed){
		this.cx = cx;
		this.cy = cy;
		this.width = width;
		this.height = height;
		this.color = color;
		this.speed = speed;
		
		this.ladoDoPlayer = false;
		this.gol = false;
		random();
		
	}

	/**
		Método chamado sempre que a bola precisa ser recolocada aleatoriamente.
	*/

	private void random() {
		Random gerador = new Random();
		int n = gerador.nextInt(50);
		positivoX = (n%2 == 0)? true : false;
		n = gerador.nextInt(50);
		positivoY = (n%2 == 0)? true : false;
	}



	/**
		Método chamado sempre que a bola precisa ser (re)desenhada.
	*/

	public void draw(){
		GameLib.setColor(color);
		GameLib.fillRect(cx, cy, width, height);
		//random();
	}

	/**
		Método chamado quando o estado (posição) da bola precisa ser atualizado.
		
		@param delta quantidade de millisegundos que se passou entre o ciclo anterior de atualização do jogo e o atual.
	*/

	public void update(long delta){
		double v = speed * delta;
		if(positivoX)
			cx = cx + v;
		else
			cx = cx - v;
		
		if(positivoY)
			cy = cy + v;
		else
			cy = cy - v;
			
		
	}

	/**
		Método chamado quando detecta-se uma colisão da bola com um jogador.
	
		@param playerId uma string cujo conteúdo identifica um dos jogadores.
	*/

	public void onPlayerCollision(String playerId){
		if (ladoDoPlayer) 
			ladoDoPlayer = false;
		else
			positivoX = (positivoX)? false : true;
		
	}

	/**
		Método chamado quando detecta-se uma colisão da bola com uma parede.

		@param wallId uma string cujo conteúdo identifica uma das paredes da quadra.
	*/

	public void onWallCollision(String wallId){
		if(gol) {
			positivoX = (positivoX)? false : true;
			gol = false;
		}else
			positivoY = (positivoY)? false : true;
	}

	/**
		Método que verifica se houve colisão da bola com uma parede.

		@param wall referência para uma instância de Wall contra a qual será verificada a ocorrência de colisão da bola.
		@return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	*/
	
	public boolean checkCollision(Wall wall){
		if((wall.getCx() + wall.getWidth()/2 > cx && wall.getCx() - wall.getWidth()/2 < cx) &&
				(wall.getCy() + wall.getHeight()/2 + height/2 > cy && wall.getCy() - wall.getHeight()/2 - height/2 < cy)) {
			if(cy + height/2 >  wall.getCy() - wall.getHeight()/2 &&
					cy - height/2 >  wall.getCy()- wall.getHeight()/2 && 
					cy + height/2 <  wall.getCy() + wall.getHeight()/2 &&
					cy - height/2 <  wall.getCy() + wall.getHeight()/2 ) {
				gol = true;
			}
			return true;
		}
		return false;
	}

	/**
		Método que verifica se houve colisão da bola com um jogador.

		@param player referência para uma instância de Player contra o qual será verificada a ocorrência de colisão da bola.
		@return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	*/	

	public boolean checkCollision(Player player){
		if((player.getCx() + player.getWidth()/2 + width/2> cx && player.getCx() - player.getWidth()/2 - width/2< cx) &&
				(player.getCy() + player.getHeight()/2 + height/2 > cy && player.getCy() - player.getHeight()/2 - height/2 < cy)) {
			
			if((cx + width/2 > player.getCx() - player.getWidth()/2 && cx - width/2 < player.getCx() + player.getWidth()) &&
					(cx + (width/2) -3 > player.getCx() - player.getWidth()/2 && cx - (width/2) +3 < player.getCx() + player.getWidth()) &&
					(cy + height/2 > player.getCy() - player.getHeight()/2 && cy - height/2 < player.getCy() + player.getHeight()) &&
					(cy + (height/2) -3 > player.getCy() - player.getHeight()/2 && cy - (height/2) + 3 < player.getCy() + player.getHeight())) {
				
				if(!(cy + height/2 >  player.getCy() - player.getHeight()/2 &&
						cy - height/2 >  player.getCy()- player.getHeight()/2 && 
						cy + height/2 <  player.getCy() + player.getHeight()/2 &&
						cy - height/2 <  player.getCy() + player.getHeight()/2 )) {
					
					if(cx > player.getCx()) 
						cx = player.getCx() + player.getWidth();
					else 
						cx = player.getCx() - player.getWidth();
					
					if(cy > player.getCy()) 
						positivoY = true;
					else 
						positivoY = false;
					
					ladoDoPlayer = true; 
				}
			}
			return true;
		}
			
		return false;
	}

	/**
		Método que devolve a coordenada x do centro do retângulo que representa a bola.
		@return o valor double da coordenada x.
	*/
	
	public double getCx(){

		return cx;
	}

	/**
		Método que devolve a coordenada y do centro do retângulo que representa a bola.
		@return o valor double da coordenada y.
	*/

	public double getCy(){

		return cy;
	}

	/**
		Método que devolve a velocidade da bola.
		@return o valor double da velocidade.

	*/

	public double getSpeed(){

		return speed;
	}

}
