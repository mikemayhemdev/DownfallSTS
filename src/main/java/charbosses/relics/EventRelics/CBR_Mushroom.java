package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.curses.EnParasite;
import charbosses.relics.AbstractCharbossRelic;
import charbosses.relics.CBR_OddMushroom;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;

import java.util.ArrayList;


public class CBR_Mushroom extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("Mushroom");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String addedName;

    public CBR_Mushroom() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/mushroom.png")));
        this.largeImg = null;
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        int choice = AbstractDungeon.cardRng.random(0,1);
        switch (choice){
            case 0:
                AbstractCharBoss.boss.chosenArchetype.addSpecificRelic(new CBR_OddMushroom(),AbstractCharBoss.boss,"Golden Idol Event", list);
                this.owner.damage(new DamageInfo(this.owner, MathUtils.floor(this.owner.maxHealth * 0.05F), DamageInfo.DamageType.HP_LOSS));
                this.description = DESCRIPTIONS[0];
            case 1:
                AbstractCharBoss.boss.chosenArchetype.addSpecificCard("Mushroom Event", new EnParasite());
                this.owner.heal(this.owner.maxHealth);
                this.description = DESCRIPTIONS[1];

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
        return new CBR_Mushroom();
    }
}
