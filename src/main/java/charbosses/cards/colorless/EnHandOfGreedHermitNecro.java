package charbosses.cards.colorless;

import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.CBR_Necronomicon;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Necronomicon;

public class EnHandOfGreedHermitNecro extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:HandOfGreed";
    private static final CardStrings cardStrings;

    public EnHandOfGreedHermitNecro() {
        super(ID, cardStrings.NAME, "colorless/attack/hand_of_greed", 2, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.RARE, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 20;
        this.baseMagicNumber = this.magicNumber = 20;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractBossCard copy = new EnHandOfGreed();
        copy.purgeOnUse = true;
        if (this.upgraded) copy.upgrade();
        copy.freeToPlayOnce = true;
        this.addToBot(new EnemyMakeTempCardInHandAction(copy, 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
            this.upgradeMagicNumber(5);
        }
    }

    private static AbstractRelic nCon = new Necronomicon();

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (AbstractCharBoss.boss != null && AbstractCharBoss.boss.hasRelic(CBR_Necronomicon.ID) && this.cost >= 2 && this.type == CardType.ATTACK) {
            nCon.currentX = this.current_x + 390.0f * this.drawScale / 3.0f * Settings.scale;
            nCon.currentY = this.current_y + 546.0f * this.drawScale / 3.0f * Settings.scale;
            nCon.scale = this.drawScale;
            nCon.renderOutline(sb, false);
            nCon.render(sb);
        }
    }

    public AbstractCard makeCopy() {
        return new EnHandOfGreedHermitNecro();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("HandOfGreed");
    }
}