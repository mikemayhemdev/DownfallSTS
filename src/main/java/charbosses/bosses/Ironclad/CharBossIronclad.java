package charbosses.bosses.Ironclad;

import java.util.ArrayList;

import charbosses.bosses.AbstractCharBoss;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbRed;

import charbosses.cards.AbstractBossCard;
import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.cards.EnemyCardGroup;
import charbosses.cards.red.*;
import charbosses.core.EnemyEnergyManager;
import charbosses.relics.CBR_BurningBlood;
import charbosses.relics.CBR_CaptainsWheel;
import charbosses.relics.CBR_Girya;
import charbosses.relics.CBR_MagicFlower;
import charbosses.relics.CBR_RedSkull;
import charbosses.relics.CBR_SelfFormingClay;
import charbosses.relics.CBR_Shuriken;
import charbosses.relics.CBR_SmoothStone;
import charbosses.relics.CBR_StrikeDummy;
import charbosses.relics.CBR_Vajra;

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

		ArrayList<AbstractBossDeckArchetype> archetypes = new ArrayList<AbstractBossDeckArchetype>();
		archetypes.add(new ArchetypeIcStrike());
		//archetypes.add(new ArchetypeIcStrength());
		//archetypes.add(new ArchetypeIcRampage());
		//archetypes.add(new ArchetypeIcBlock());

		this.chosenArchetype = archetypes.get(AbstractDungeon.monsterRng.random(archetypes.size() - 1));

		this.chosenArchetype.initialize();

		this.chosenArchetype.simulateBuild(AbstractCharBoss.boss);



	}

}


