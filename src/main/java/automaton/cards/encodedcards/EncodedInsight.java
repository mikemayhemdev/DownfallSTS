
package automaton.cards.encodedcards;

import automaton.cards.AbstractBronzeCard;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightBulbEffect;
import downfall.cardmods.RetainCardMod;

public class EncodedInsight extends AbstractBronzeCard {
    public static final String ID = "bronze:EncodedInsight";
    private static final CardStrings cardStrings;

    public EncodedInsight() {
        super("bronze:EncodedInsight", cardStrings.NAME, "colorless/skill/insight", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        CardModifierManager.addModifier(this, new RetainMod());
    }

    @Override
    public void upp() {
        upgrade();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new LightBulbEffect(p.hb)));
        } else {
            this.addToBot(new VFXAction(new LightBulbEffect(p.hb), 0.2F));
        }

        this.addToBot(new DrawCardAction(p, this.magicNumber));
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        CardModifierManager.addModifier(function, new RetainCardMod());
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }

    }

    public AbstractCard makeCopy() {
        return new EncodedInsight();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("bronze:EncodedInsight");
    }
}
