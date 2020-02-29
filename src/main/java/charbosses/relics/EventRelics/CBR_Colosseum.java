package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.curses.EnShame;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_Colosseum extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("Colosseum");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String addedName;
    private String addedName2;
    private String addedName3;

    public CBR_Colosseum() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/colosseum.png")));
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        addedName = AbstractCharBoss.boss.chosenArchetype.addRandomGlobalRelicOfRarity(AbstractCharBoss.boss, "Colosseum", RelicTier.RARE);
        addedName2 = AbstractCharBoss.boss.chosenArchetype.addRandomGlobalRelicOfRarity(AbstractCharBoss.boss, "Colosseum", RelicTier.UNCOMMON);
        addedName3 = AbstractCharBoss.boss.chosenArchetype.addRandomSynergyCard("Colosseum");
        this.owner.damage(new DamageInfo(this.owner, MathUtils.floor(this.owner.maxHealth * 0.2F), DamageInfo.DamageType.HP_LOSS));

        this.description = getUpdatedDescription();
        refreshDescription();
    }
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.addedName + ", " + this.addedName2 + ", & " + this.addedName3 + DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Colosseum();
    }
}
