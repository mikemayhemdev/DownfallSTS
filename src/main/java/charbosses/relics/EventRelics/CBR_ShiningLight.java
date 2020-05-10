package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;

import java.util.ArrayList;


public class CBR_ShiningLight extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("ShiningLight");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String cardName;
    private String cardName2;

    public CBR_ShiningLight() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/shininglight.png")));
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        cardName = AbstractCharBoss.boss.chosenArchetype.upgradeRandomCard("Shining Light");
        cardName2 = AbstractCharBoss.boss.chosenArchetype.upgradeRandomCard("Shining Light");
        this.owner.damage(new DamageInfo(this.owner, MathUtils.floor(this.owner.maxHealth * 0.08F), DamageInfo.DamageType.HP_LOSS));

        this.description = getUpdatedDescription();
        refreshDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.cardName + this.DESCRIPTIONS[1] + this.cardName2 +  this.DESCRIPTIONS[2];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_ShiningLight();
    }
}
