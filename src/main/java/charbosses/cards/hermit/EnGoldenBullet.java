package charbosses.cards.hermit;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.actions.GoldenBulletAction;
import hermit.cards.GoldenBullet;
import hermit.cards.Grudge;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;

public class EnGoldenBullet extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:GoldenBullet";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(GoldenBullet.ID);

    public EnGoldenBullet(int damage) {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/golden_bullet.png", 3, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = damage;
        this.baseMagicNumber = this.magicNumber = 2;
    }

    public EnGoldenBullet() {
        this(10);
    }


    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GoldenBulletAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), 1, this.uuid));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(6);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnGoldenBullet();
    }
}
