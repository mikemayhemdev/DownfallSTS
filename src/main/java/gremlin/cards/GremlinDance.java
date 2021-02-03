package gremlin.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gremlin.characters.GremlinCharacter;

public class GremlinDance extends AbstractGremlinCard {
    private static final String ID = getID("GremlinDance");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/gremlin_dance.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;

    private static final int COST = 1;
    private static final int POWER = 6;
    private static final int BLOCK = 6;
    private static final int MAGIC = 2;
    private static final int UPGRADE_BONUS = 3;

    public GremlinDance()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = POWER;
        this.baseBlock = BLOCK;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        String gremlin = "";
        boolean isNob = false;
        if(AbstractDungeon.player instanceof GremlinCharacter) {
            if(((GremlinCharacter) AbstractDungeon.player).nob){
                isNob = true;
            } else {
                gremlin = ((GremlinCharacter) AbstractDungeon.player).currentGremlin;
            }
        }

        if(gremlin.equals("shield")){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        }

        if(gremlin.equals("angry")){
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage,
                    this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        } else if(gremlin.equals("wizard")){
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                    this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        } else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                    this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

        if(gremlin.equals("fat")){
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(m, p, new StrengthPower(m, -this.magicNumber), -this.magicNumber));
            if (!m.hasPower("Artifact")) {
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(m, p, new GainStrengthPower(m, this.magicNumber), this.magicNumber));
            }
        }

        if(gremlin.equals("sneak")){
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        }

        if(isNob){
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        }
    }

    public AbstractCard makeCopy()
    {
        return new GremlinDance();
    }

    @Override
    public void applyPowers() {
        updateContents();
        super.applyPowers();
    }

    private void updateContents(){
        this.rawDescription = strings.EXTENDED_DESCRIPTION[0];
        if(AbstractDungeon.player instanceof GremlinCharacter){
            if(((GremlinCharacter) AbstractDungeon.player).nob){
                rawDescription += strings.EXTENDED_DESCRIPTION[6];
                this.isMultiDamage = false;
                this.target = CardTarget.ENEMY;
                this.wizardry = false;
            } else {
                String gremlin = ((GremlinCharacter) AbstractDungeon.player).currentGremlin;
                switch (gremlin) {
                    case "angry":
                        rawDescription += strings.EXTENDED_DESCRIPTION[1];
                        this.isMultiDamage = true;
                        this.target = CardTarget.ALL_ENEMY;
                        this.wizardry = false;
                        break;
                    case "fat":
                        rawDescription += strings.EXTENDED_DESCRIPTION[2];
                        this.isMultiDamage = false;
                        this.target = CardTarget.ENEMY;
                        this.wizardry = false;
                        break;
                    case "shield":
                        rawDescription += strings.EXTENDED_DESCRIPTION[3];
                        this.isMultiDamage = false;
                        this.target = CardTarget.ENEMY;
                        this.wizardry = false;
                        break;
                    case "sneak":
                        rawDescription += strings.EXTENDED_DESCRIPTION[4];
                        this.isMultiDamage = false;
                        this.target = CardTarget.ENEMY;
                        this.wizardry = false;
                        break;
                    case "wizard":
                        rawDescription += strings.EXTENDED_DESCRIPTION[5];
                        this.isMultiDamage = false;
                        this.target = CardTarget.ENEMY;
                        this.wizardry = true;
                        break;
                }
            }
        }
        initializeDescription();
    }

    @Override
    public void onGremlinSwapInDeck() {
        updateContents();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_BONUS);
        }
    }
}
