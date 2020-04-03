package charbosses.bosses.Merchant;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeAct1PerfectedStrike;
import charbosses.bosses.Ironclad.ArchetypeAct2Strength;
import charbosses.bosses.Ironclad.ArchetypeAct3Block;
import charbosses.core.EnemyEnergyManager;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbRed;

public class CharBossMerchant extends AbstractCharBoss {



    public CharBossMerchant() {
        super("Merchant", "EvilWithin:Merchant", 200, -4.0f, -16.0f, 220.0f, 290.0f, null, 0.0f, -20.0f, PlayerClass.IRONCLAD);
        this.energyOrb = new EnergyOrbRed();
        this.energy = new EnemyEnergyManager(3);

        drawX = 1260.0F * Settings.scale;
        drawY = 370.0F * Settings.scale;

        loadAnimation("images/npcs/merchant/skeleton.atlas", "images/npcs/merchant/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(1.0F);

        this.energyString = "[R]";


    }

    @Override
    public void generateDeck() {
        //ArrayList<AbstractBossDeckArchetype> archetypes = new ArrayList<AbstractBossDeckArchetype>();
        AbstractBossDeckArchetype archetype = new ArchetypeAct3MerchantBoss();

        archetype.initialize();

    }

}


