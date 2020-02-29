package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.curses.EnDecay;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_WheelOfChange extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("WheelOfChange");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String addedName;

    public CBR_WheelOfChange() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/wheelofchange.png")));
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        int choice = AbstractDungeon.cardRng.random(0,4);
        switch (choice){
            case 0:
                addedName = AbstractCharBoss.boss.chosenArchetype.addRandomGlobalRelic(actIndex, AbstractCharBoss.boss, list);
                this.description = DESCRIPTIONS[0] + this.addedName;
            case 1:
                this.owner.heal(this.owner.maxHealth);
                this.description = DESCRIPTIONS[1];
            case 2:
                AbstractCharBoss.boss.chosenArchetype.addSpecificCard("Wheel of Change", new EnDecay());
                this.description = DESCRIPTIONS[0] + this.addedName;
            case 3:
                addedName = AbstractCharBoss.boss.chosenArchetype.removeBasicCard("Wheel of Change");
                this.description =  DESCRIPTIONS[2] + this.addedName + DESCRIPTIONS[3];
            case 4:
                this.owner.damage(new DamageInfo(this.owner, MathUtils.floor(this.owner.maxHealth * 0.15F), DamageInfo.DamageType.HP_LOSS));
                this.description = DESCRIPTIONS[4];

        }

        this.description = getUpdatedDescription();
        this.refreshDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_WheelOfChange();
    }
}
