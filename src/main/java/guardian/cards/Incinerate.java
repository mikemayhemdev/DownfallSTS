package guardian.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.actions.ReduceRightMostStasisAction;
import guardian.patches.AbstractCardEnum;

import static guardian.GuardianMod.makeBetaCardPath;


public class Incinerate extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("Incinerate");
    public static final String IMG_PATH = GuardianMod.getResourcePath("cards/incinerate.png");
    private static final CardStrings cardStrings;

    public Incinerate() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.ATTACK, AbstractCardEnum.GUARDIAN, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.socketCount = 1;
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("Incinerate.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new ReduceRightMostStasisAction());
        if (upgraded) addToBot(new ReduceRightMostStasisAction());
        super.useGems(p, m);
    }

    public AbstractCard makeCopy() {
        return new Incinerate();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            //rawDescription = UPGRADED_DESCRIPTION;
            //initializeDescription();
            updateDescription();
        }
    }

    public void updateDescription() {
        if (this.socketCount > 0) {
            if (upgraded && cardStrings.UPGRADE_DESCRIPTION != null) {
                this.rawDescription = this.updateGemDescription(cardStrings.UPGRADE_DESCRIPTION, true);
            } else {
                this.rawDescription = this.updateGemDescription(cardStrings.DESCRIPTION, true);
            }
        }
        this.initializeDescription();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


