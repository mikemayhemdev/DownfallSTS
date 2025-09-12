package charbosses.cards.hermit;

import charbosses.bosses.Hermit.NewAge.ArchetypeAct3DoomsdayNewAge;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.SnakeDagger;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import downfall.downfallMod;
import hermit.cards.Purgatory;
import hermit.characters.hermit;
import hermit.vfx.ShortScreenFire;

public class EnPurgatory extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Purgatory";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Purgatory.ID);

    public EnPurgatory() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/purgatory.png", 3, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.RARE, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 24;
        isEthereal = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ShortScreenFire(), 0.5F));
        addToBot(new DamageAction(p, new DamageInfo(m, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));

        if (downfallMod.useLegacyBosses) {
            for (AbstractMonster m2 : AbstractDungeon.getMonsters().monsters) {
                if (!m2.isDead && !m2.isDying && m2 instanceof SnakeDagger) {
                    addToBot(new VFXAction(m2, new InflameEffect(m), 0.2F));
                    addToBot(new SuicideAction(m2));
                    addToBot(new HideHealthBarAction(m2));
                }
            }
            addToBot(new SpawnMonsterAction(ArchetypeAct3DoomsdayNewAge.getDoomedSnake(), true));
        }
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
        return new EnPurgatory();
    }
}