/*
package charbosses.bosses;

import java.util.ArrayList;

import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbGreen;

import charbosses.cards.AbstractBossCard;
import charbosses.cards.EnemyCardGroup;
import charbosses.cards.green.*;
import charbosses.core.EnemyEnergyManager;
import charbosses.relics.*;

public class CharBossSilent  extends AbstractCharBoss {
	
	public CharBossSilent() {
		super("Silent", "EvilWithin:Silent", 80, -4.0f, -16.0f, 220.0f, 290.0f, null, 0.0f, 0.0f, PlayerClass.THE_SILENT);
		this.energyOrb = new EnergyOrbGreen();
		this.energy = new EnemyEnergyManager(3);
		this.loadAnimation("images/characters/theSilent/idle/skeleton.atlas", "images/characters/theSilent/idle/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1f);
        this.flipHorizontal = true;
        e.setTimeScale(0.9f);
        this.startingRelic = new CBR_SnakeRing();
	}


	public static final String ARCHETYPE_SL_SHIV = "SL_SHIV_ARCHETYPE";
	public static final String ARCHETYPE_SL_POISON = "SL_POISON_ARCHETYPE";
	public static final String ARCHETYPE_SL_DISCARD = "SL_DISCARD_ARCHETYPE";
	
	@Override
	public void generateDeck() {
		for (int i=0; i < 5; i++) {
			this.masterDeck.addToTop(new EnDefendGreen());
			this.masterDeck.addToTop(new EnStrikeGreen());
		}
		this.masterDeck.addToTop(new EnNeutralize());
		this.masterDeck.addToTop(new EnSurvivor());
		ArrayList<AbstractBossDeckArchetype> archetypes = new ArrayList<AbstractBossDeckArchetype>();
		archetypes.add(new ArchetypeSlShiv());
		this.chosenArchetype = archetypes.get(AbstractDungeon.monsterRng.random(archetypes.size() - 1));
		for (AbstractBossCard c : this.chosenArchetype.buildCardList()) {
			this.masterDeck.addToTop(c);
		}
		for (int i=0; i < (AbstractDungeon.actNum - 1) * 2; i++) {
			this.masterDeck.group.remove(0);
		}
		if (AbstractDungeon.actNum > 1) {
			((EnemyCardGroup) this.masterDeck).getHighestUpgradeValueCard().upgrade();
		}
		for (int i=0; i < AbstractDungeon.actNum; i++) {
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
		generallyGoodCards.add(new EnCloakAndDagger());
		generallyGoodCards.add(new EnDodgeAndRoll());
		generallyGoodCards.add(new EnOutmaneuver());
		generallyGoodCards.add(new EnPoisonedStab());
		generallyGoodCards.add(new EnDaggerSpray());
	}
	

	public static class ArchetypeSlShiv extends AbstractBossDeckArchetype {

		public ArchetypeSlShiv() {
			super(ARCHETYPE_SL_SHIV);
			this.allCards.add(new EnBladeDance());
			this.allCards.add(new EnCloakAndDagger());
			this.allCards.add(new EnFinisher());
			this.allCards.add(new EnAccuracy());
			this.allCards.add(new EnInfiniteBlades());
		}

		@Override
		public ArrayList<AbstractBossCard> buildCardList() {
			ArrayList<AbstractBossCard> cards = new ArrayList<AbstractBossCard>();
			for (int i=0; i < 2; i++) {
				cards.add(this.getRandomCard(cards, CardRarity.COMMON));
			}
			for (int i=0; i < 2 * AbstractDungeon.actNum - 1; i++) {
				cards.add(this.getRandomCard(cards));
			}
			cards.add(this.getRandomCard(cards, CardRarity.UNCOMMON));
			for (int i=0; i < 3 - AbstractDungeon.actNum; i++) {
				cards.add(this.getRandomCard(cards, generallyGoodCards, CardRarity.COMMON));
			}
			for (int i=0; i < AbstractDungeon.actNum; i++) {
				cards.add(this.getRandomCard(cards, generallyGoodCards, CardRarity.RARE, true));
			}
			return cards;
		}
		
	}
}
*/
