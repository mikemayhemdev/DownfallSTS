package automaton.cards;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import automaton.cardmods.FullReleaseCardMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.AutomatonMod.makeBetaCardPath;

public class FullRelease extends AbstractBronzeCard {

    public final static String ID = makeID("FullRelease");
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("bronze:AutoTextHelper");


    public FullRelease() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        thisEncodes();
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("FullRelease.png"));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (canUse) {

            for (AbstractCard c : FunctionHelper.held.group) {
                if (c instanceof FullRelease) {
                    canUse = false;
                    break;
                }
            }
            if (!canUse) this.cantUseMessage = uiStrings.TEXT[6];
        }
        return canUse;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void onCompileFirst(AbstractCard function, boolean forGameplay) {
        CardModifierManager.addModifier(function, new FullReleaseCardMod());
    }

    @Override
    public String getBonusChar() {
        return EXTENDED_DESCRIPTION[2];
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}