package charbosses.cards.red;

import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import downfall.downfallMod;

public class EnFeed extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Feed";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Feed");
    }

    public EnFeed() {
        super(ID, cardStrings.NAME, "red/attack/feed", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.RARE, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 10;
        this.exhaust = true;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTags.HEALING);

    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new VFXAction(new BiteEffect(p.hb.cX, p.hb.cY - 40.0F * Settings.scale, Color.SCARLET.cpy()), 0.3F));

        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnFeed();
    }
}
