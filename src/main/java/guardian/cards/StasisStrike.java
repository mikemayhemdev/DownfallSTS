package guardian.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;

import static guardian.GuardianMod.makeBetaCardPath;

public class StasisStrike extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("StasisStrike");
    public static final String IMG_PATH = GuardianMod.getResourcePath("cards/stasisStrike.png");
    private static final CardStrings cardStrings;

    public StasisStrike() {
        super(ID, cardStrings.NAME, IMG_PATH, 2, cardStrings.DESCRIPTION, CardType.ATTACK, AbstractCardEnum.GUARDIAN, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 16;
        this.socketCount = 0;
        this.tags.add(CardTags.STRIKE);
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("StasisStrike.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new IncreaseMaxOrbAction(1));
        this.useGems(p, m);
    }

    public AbstractCard makeCopy() {
        return new StasisStrike();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(4);
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


