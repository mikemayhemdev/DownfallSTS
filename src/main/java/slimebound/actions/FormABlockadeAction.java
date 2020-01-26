package slimebound.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.ShieldParticleEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.orbs.SpawnedSlime;
import slimebound.vfx.ShieldParticleEffectInFront;

import java.util.ArrayList;
import java.util.List;

public class FormABlockadeAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int block;

    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());


    public FormABlockadeAction(int block) {

        this.block = block;

        this.p = AbstractDungeon.player;

        setValues(this.p, AbstractDungeon.player, this.amount);

        this.actionType = ActionType.CARD_MANIPULATION;

        this.duration = Settings.ACTION_DUR_FAST;

    }


    public void update() {

        for (AbstractOrb o : p.orbs) {

            if (o instanceof SpawnedSlime) {
                com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShieldParticleEffectInFront(o.cX, o.cY)));
            }

        }



        this.isDone = true;




        tickDuration();
    }
}




