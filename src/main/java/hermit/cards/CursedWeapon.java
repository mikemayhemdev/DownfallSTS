package hermit.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import hermit.HermitMod;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;
import hermit.util.Wiz;

import static hermit.HermitMod.*;

public class CursedWeapon extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(CursedWeapon.class.getSimpleName());
    public static final String IMG = makeCardPath("cursed_weapon.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static int BONUS = 0;
    private static final int COST = 0;

    private static final int DAMAGE = 6;

    // /STAT DECLARATION/

    public CursedWeapon() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE + BONUS;
        magicNumber = this.baseMagicNumber = 2;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 1;
        this.exhaust = true;
        tags.add(CardTags.HEALING);
        loadJokeCardImage(this, "cursed_weapon.png");
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new LoseHPAction(p, p, magicNumber));
        Wiz.atb(new AbstractGameAction(){
            @Override
            public void update()
            {
                actionType = ActionType.DAMAGE;
                DamageInfo info = new DamageInfo(p, damage, damageTypeForTurn);
                setValues(m, info);

                if (target != null) {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, EnumPatch.HERMIT_GHOSTFIRE));
                    target.damage(info);

                    for(AbstractCard c : Wiz.p().exhaustPile.group)
                        if (c instanceof CursedWeapon)
                            c.baseDamage += defaultSecondMagicNumber;

                    // Update all cursed weapons in deck.
                    for(AbstractCard c : Wiz.p().masterDeck.group)
                        if (c instanceof CursedWeapon)
                            c.baseDamage += defaultSecondMagicNumber;

                    // Update BONUS for future cursed weapons.
                    CursedWeapon.BONUS += defaultSecondMagicNumber;

                    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                        AbstractDungeon.actionManager.clearPostCombatActions();
                    }
                }

                isDone = true;
            }
        });
    }

    public void triggerOnCardPlayed(AbstractCard c) {
        if (c instanceof CursedWeapon) {
            baseDamage += ((CursedWeapon)c).defaultSecondMagicNumber;
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(1);
        }
    }
}