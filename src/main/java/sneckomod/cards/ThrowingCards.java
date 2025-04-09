package sneckomod.cards;

import automaton.actions.EasyXCostAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.FTL;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.HoleUp;
import sneckomod.SneckoMod;

public class ThrowingCards extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("ThrowingCards");

    public ThrowingCards() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        cardsToPreview = new FTL();
        exhaust = true;
        baseMagicNumber = magicNumber = 0;
        SneckoMod.loadJokeCardImage(this, "ThrowingCards.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0; i < effect + params[0]; i++) {
                AbstractCard g = new FTL();
                if (this.upgraded) {
                    g.upgrade();
                }
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(g));
            }
            return true;
        }, magicNumber));
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            AbstractCard q = new FTL();
            q.upgrade();
            cardsToPreview = q;
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}