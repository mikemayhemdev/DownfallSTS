package collector.util;

import collector.CollectorChar;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class TorchHead extends AbstractCreature {
    public int KindleStr = 0;
    public int BasePower = 5;
    public static TextureAtlas Torch = new TextureAtlas("collectorResources\\images\\TorchHead");
    public TorchHead(){
        super();
        isPlayer = false;
       maxHealth = 45;
    }
    public static void init(){
        CollectorChar.TorchHead = new TorchHead();
    }

    public void Command(){
        AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true).damage(new DamageInfo(this,BasePower, DamageInfo.DamageType.NORMAL));
    }
    public void CommandAt(AbstractMonster m){
        m.damage(new DamageInfo(this,BasePower, DamageInfo.DamageType.NORMAL));
    }

    public void CommandAll(){
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            m.damage(new DamageInfo(this,BasePower, DamageInfo.DamageType.NORMAL));
        }
    }

    public void onBattleStart(){

        if (KindleStr > 0){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new StrengthPower(this,KindleStr)));
        }
    }

    @Override
    public void damage(DamageInfo damageInfo) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }
}
