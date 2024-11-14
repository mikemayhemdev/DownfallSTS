package sneckomod.cards;

import automaton.actions.AddToFuncAction;
import automaton.cards.FormatEncoded;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.FTL;
import com.megacrit.cardcrawl.cards.purple.Wallop;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import sneckomod.SneckoMod;
import automaton.actions.EasyXCostAction;

public class ThrowingCards extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("ThrowingCards");

    public ThrowingCards() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        cardsToPreview = new FTL();
        exhaust = true;
        baseMagicNumber = magicNumber = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0; i < effect + params[0]; i++) {
                AbstractCard g = new Wallop();
                if(this.upgraded){
                    g.upgrade();
                }
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(g));
            }
            return true;
        }, magicNumber));
    }

    @Override
        public void upgrade() {
            if (!upgraded) {
                upgradeName();
                rawDescription = UPGRADE_DESCRIPTION;
                initializeDescription();
            }
        }
    }