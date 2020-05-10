package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnBite;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;

import java.util.ArrayList;


public class CBR_Vampires extends AbstractCharbossRelic {
    public static String ID = downfallMod.makeID("Vampires");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = AbstractRelic.LandingSound.MAGICAL;

    public int hpLoss;

    public CBR_Vampires() {
        super(ID, tier, sound, new Texture(downfallMod.assetPath("images/relics/vampires.png")));

    }


    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        ArrayList<AbstractBossCard> cardsToRemove = new ArrayList<>();
        for (AbstractBossCard c : list) {
            if (c.hasTag(AbstractBossCard.CardTags.STARTER_STRIKE)) {
                AbstractBossDeckArchetype.logger.info("Vampires event removed 1 " + c.name + ".");
                cardsToRemove.add(c);
            }
        }
        if (cardsToRemove.size() > 0) {
            for (AbstractBossCard c : cardsToRemove) {
                list.remove(c);
            }
        }
        for (int i = 0; i < 5; i++) {

            AbstractCharBoss.boss.masterDeck.group.add(new EnBite());
            AbstractBossDeckArchetype.logger.info("Vampires event added 1 Bite.");
        }

        AbstractBossDeckArchetype.logger.info("Boss's current max HP is " + AbstractCharBoss.boss.maxHealth);
        this.hpLoss = MathUtils.floor(AbstractCharBoss.boss.maxHealth * 0.3F);
        AbstractBossDeckArchetype.logger.info("Boss's modified max HP is " + this.hpLoss);
        AbstractCharBoss.boss.decreaseMaxHealth(MathUtils.floor(AbstractCharBoss.boss.maxHealth * 0.3F));
        AbstractBossDeckArchetype.logger.info("Boss's new max HP is " + AbstractCharBoss.boss.maxHealth);
        this.updateDescription(AbstractCharBoss.boss.chosenClass);
    }

    @Override
    public void updateDescription(final AbstractPlayer.PlayerClass c) {
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.hpLoss + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Vampires();
    }
}
