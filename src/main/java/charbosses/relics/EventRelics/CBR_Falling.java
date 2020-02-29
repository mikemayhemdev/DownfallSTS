package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_Falling extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("Falling");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;

    public String cardName = "";

    public CBR_Falling() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/falling.png")));

    }


    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        AbstractBossCard cardToRemove;
        ArrayList<AbstractBossCard> nonRareCards = new ArrayList<>();

        //Boss balance fakery - Falling can't pick Rares.  It can't pick Curses anyway (power/skill/attack only)
        for (AbstractBossCard c : list) {
            if (c.rarity != AbstractCard.CardRarity.RARE && c.rarity != AbstractCard.CardRarity.CURSE) {
                nonRareCards.add(c);
            }
        }
        cardToRemove = nonRareCards.get(AbstractDungeon.cardRng.random(0, nonRareCards.size() - 1));
        AbstractBossDeckArchetype.logger.info("Falling event removed 1 " + cardToRemove.name + ".");
        cardName = cardToRemove.name;
        list.remove(cardToRemove);

        this.description = getUpdatedDescription();
        refreshDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return this.cardName + this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Falling();
    }
}
