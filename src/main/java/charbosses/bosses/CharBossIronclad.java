package charbosses.bosses;

import java.util.ArrayList;

import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbRed;

import charbosses.cards.AbstractBossCard;
import charbosses.cards.AbstractBossDeckArchetype;
import charbosses.cards.EnemyCardGroup;
import charbosses.cards.red.*;
import charbosses.core.EnemyEnergyManager;

public class CharBossIronclad extends AbstractCharBoss {
	
	public CharBossIronclad() {
		super("Ironclad", "EvilWithin:Ironclad", 80, -4.0f, -16.0f, 220.0f, 290.0f, null, 0.0f, 0.0f, PlayerClass.IRONCLAD);
		this.energyOrb = new EnergyOrbRed();
		this.energy = new EnemyEnergyManager(3);
		this.loadAnimation("images/characters/ironclad/idle/skeleton.atlas", "images/characters/ironclad/idle/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1f);
        this.flipHorizontal = true;
        e.setTimeScale(0.6f);
	}


	public static final String ARCHETYPE_IC_STRIKE = "IC_STRIKE_ARCHETYPE";
	public static final String ARCHETYPE_IC_STRENGTH = "IC_STRENGTH_ARCHETYPE";
	public static final String ARCHETYPE_IC_BLOCK = "IC_BLOCK_ARCHETYPE";
	
	@Override
	public void generateDeck() {
		for (int i=0; i < 5; i++) {
			this.masterDeck.addToTop(new EnStrikeRed());
			this.masterDeck.addToTop(new EnDefendRed());
		}
		this.masterDeck.addToTop(new EnBash());
		if (AbstractDungeon.monsterRng.randomBoolean()) {
			this.chosenArchetype = new ArchetypeIcStrike();
		} else {
			this.chosenArchetype = new ArchetypeIcStrength();
		}
		for (AbstractBossCard c : this.chosenArchetype.buildCardList()) {
			this.masterDeck.addToTop(c);
		}
		for (int i=0; i < 3; i++) {
			AbstractCard c = this.masterDeck.getRandomCard(false);
			if (c.canUpgrade()) {
				c.upgrade();
			} else {
				i -= 1;
			}
		}
		
	}
	
	public static ArrayList<AbstractBossCard> generallyGoodCards;
	static {
		generallyGoodCards = new ArrayList<AbstractBossCard>();
		generallyGoodCards.add(new EnArmaments());
		generallyGoodCards.add(new EnTwinStrike());
		generallyGoodCards.add(new EnHeadbutt());
		generallyGoodCards.add(new EnIronWave());
		generallyGoodCards.add(new EnFlex());
	}

	public static class ArchetypeIcStrike extends AbstractBossDeckArchetype {

		public ArchetypeIcStrike() {
			super(ARCHETYPE_IC_STRIKE);
			this.allCards.add(new EnPerfectedStrike());
			this.allCards.add(new EnWildStrike());
			this.allCards.add(new EnTwinStrike());
			this.allCards.add(new EnHeadbutt());
		}

		@Override
		public ArrayList<AbstractBossCard> buildCardList() {
			ArrayList<AbstractBossCard> cards = new ArrayList<AbstractBossCard>();
			cards.add(new EnPerfectedStrike());
			Random rand = new Random();
			for (int i=0; i < 2 * AbstractDungeon.actNum; i++) {
				cards.add((AbstractBossCard)this.allCards.get(rand.random(this.allCards.size() - 1)).makeCopy());
			}
			for (int i=0; i < 4; i++) {
				cards.add((AbstractBossCard)generallyGoodCards.get(rand.random(generallyGoodCards.size() - 1)).makeCopy());
			}
			return cards;
		}
		
	}
	public static class ArchetypeIcStrength extends AbstractBossDeckArchetype {

		public ArchetypeIcStrength() {
			super(ARCHETYPE_IC_STRENGTH);
			this.allCards.add(new EnTwinStrike());
			this.allCards.add(new EnInflame());
			this.allCards.add(new EnFlex());
			this.allCards.add(new EnSwordBoomerang());
		}

		@Override
		public ArrayList<AbstractBossCard> buildCardList() {
			ArrayList<AbstractBossCard> cards = new ArrayList<AbstractBossCard>();
			Random rand = new Random();
			for (int i=0; i < 2 + AbstractDungeon.actNum * 2; i++) {
				cards.add((AbstractBossCard)this.allCards.get(rand.random(this.allCards.size() - 1)).makeCopy());
			}
			for (int i=0; i < 5; i++) {
				cards.add((AbstractBossCard)generallyGoodCards.get(rand.random(generallyGoodCards.size() - 1)).makeCopy());
			}
			return cards;
		}
		
	}
}
