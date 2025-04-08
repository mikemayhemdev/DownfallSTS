package expansioncontent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import expansioncontent.expansionContentMod;
import slimebound.SlimeboundMod;
import slimebound.powers.SlimedPower;
import slimebound.vfx.SlimeProjectileEffect;

import java.util.Iterator;

public class GoopSpray extends AbstractExpansionCard {
    public static final String ID = makeID("SuperGoopSpray");

    private static final int MAGIC = 8;

    private static final int UPGRADE_MAGIC = 3;

    private static final int downfallMagic = 2;

    private static final int UPGRADE_downfallMagic = 1;

    public GoopSpray() {
        super(ID, 1, AbstractCard.CardType.SKILL, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
        setBackgroundTexture("expansioncontentResources/images/512/bg_boss_slime.png", "expansioncontentResources/images/1024/bg_boss_slime.png");
        this.tags.add(expansionContentMod.STUDY_SLIMEBOSS);
        this.tags.add(expansionContentMod.STUDY);
        this.baseDownfallMagic = 2;
        this.baseMagicNumber = this.magicNumber = 8;
        expansionContentMod.loadJokeCardImage((AbstractCard)this, "GoopSpray.png");
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded) {
            atb((AbstractGameAction)new VFXAction((AbstractGameEffect)new SlimeProjectileEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY, 3.0F, false, 0.6F), 0.01F));
            atb((AbstractGameAction)new WaitAction(0.2F));
            atb((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)p, (AbstractPower)new SlimedPower((AbstractCreature)m, (AbstractCreature)p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));

            this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, downfallMagic, false), downfallMagic));
        } else {
            Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            while(var3.hasNext()) {
                AbstractMonster mo = (AbstractMonster)var3.next();
                atb((AbstractGameAction)new VFXAction((AbstractGameEffect)new SlimeProjectileEffect(p.hb.cX, p.hb.cY, mo.hb.cX, mo.hb.cY, 3.0F, false, 0.6F), 0.01F));
                atb((AbstractGameAction)new ApplyPowerAction((AbstractCreature)mo, (AbstractCreature)p, (AbstractPower)new SlimedPower((AbstractCreature)mo, (AbstractCreature)p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                this.addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, downfallMagic, false), downfallMagic, true, AbstractGameAction.AttackEffect.NONE));
            }
        }

    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            this.target = CardTarget.ALL_ENEMY;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
