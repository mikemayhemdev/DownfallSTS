package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FusionHammer;
import com.megacrit.cardcrawl.relics.Shovel;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;

public class CBR_Shovel extends AbstractCharbossRelic {
    public static final String ID = "Shovel";
    private int numRelics;

    public CBR_Shovel() {
        super(new Shovel());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + CardCrawlGame.languagePack.getRelicStrings(EvilWithinMod.makeID(ID)).DESCRIPTIONS[0] + this.numRelics + CardCrawlGame.languagePack.getRelicStrings(EvilWithinMod.makeID(ID)).DESCRIPTIONS[1];
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify, int actIndex) {

        for (int i = actIndex; i < 3; i++) {
            AbstractCharBoss.boss.chosenArchetype.addRandomGlobalRelic(actIndex,this.owner,"Black Star", groupToModify);
            this.numRelics++;
        }

        this.description = getUpdatedDescription();
        this.refreshDescription();

    }

    @Override
    public void onEquip() {
        this.owner.damage(new DamageInfo(this.owner, MathUtils.floor(this.owner.maxHealth * 0.15F), DamageInfo.DamageType.HP_LOSS));

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Shovel();
    }
}
