package gremlin.powers;

import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import gremlin.cards.Bellow;
import gremlin.cards.Rush;
import gremlin.cards.SkullBash;
import gremlin.characters.GremlinCharacter;

public class GremlinNobPower extends GremlinPower implements OnLoseTempHpPower {
    public static final String POWER_ID = getID("GremlinNob");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public GremlinNobPower(int amount) {
        super();
        this.pot = amount;
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = strings.DESCRIPTIONS[0];
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        ((GremlinCharacter)(AbstractDungeon.player)).becomeNob();
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Bellow(), 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new SkullBash(), 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Rush(), 1));
    }

    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int damageAmount) {
        int tempHp = TempHPField.tempHp.get(AbstractDungeon.player);
        if(damageAmount >= tempHp){
            ((GremlinCharacter)(AbstractDungeon.player)).revertNob();
            return tempHp;
        }
        return damageAmount;
    }
}
