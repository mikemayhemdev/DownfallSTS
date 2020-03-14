package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_UpgradeShrine extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("UpgradeShrine");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    private String cardName;

    public CBR_UpgradeShrine() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/shrine2.png")));
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        cardName = AbstractCharBoss.boss.chosenArchetype.upgradeRandomCard("Upgrade Shrine");

        this.description = getUpdatedDescription();
        refreshDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_UpgradeShrine();
    }
}
