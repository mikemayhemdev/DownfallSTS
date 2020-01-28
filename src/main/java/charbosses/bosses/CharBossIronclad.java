package charbosses.bosses;

import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbRed;

import charbosses.cards.AbstractBossCard;
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

	@Override
	public void generateDeck() {
		for (int i=0; i < 5; i++) {
			this.masterDeck.addToTop(new EnStrikeRed());
			this.masterDeck.addToTop(new EnDefendRed());
		}
		this.masterDeck.addToTop(new EnBash());
		this.masterDeck.addToTop(new EnInflame());
		
		
	}
}
